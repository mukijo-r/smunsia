package com.example.smunsia1

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.smunsia1.ui.AuthViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.util.UUID

@Composable
fun PostPage(navController: NavController, authViewModel: AuthViewModel, username: String) {
    var username = username
    var descriptionText by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val pickImageLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
        // Handle the selected image URI
        selectedImageUri = uri
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Buat postingan") },
                backgroundColor = Color.LightGray,
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            content = {
                items(
                    items = listOf(
                        Post(
                            imageId = R.drawable.image1,
                            title = "Caption :",
                            description = descriptionText,
                        ),
                    )
                ) { post ->
                    PostItem(
                        post = post,
                        username = username,
                        onDescriptionChanged = { descriptionText = it },
                        onImageClick = { pickImageLauncher.launch("image/*") },
                        selectedImageUri = selectedImageUri, // Kirim selectedImageUri ke PostItem
                        onImageSelected = { uri -> selectedImageUri = uri }, // Update selectedImageUri dari PostItem
                        navController = navController
                    )
                    selectedImageUri?.let { uri ->
                        Image(
                            painter = rememberImagePainter(uri),
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth().size(200.dp)
                        )
                    }

                }
            }
        )
    }
}

@Composable
fun PostItem(
    post: Post,
    username: String,
    onDescriptionChanged: (String) -> Unit,
    onImageClick: () -> Unit,
    selectedImageUri: Uri?, // Terima selectedImageUri dari PostPage
    onImageSelected: (Uri?) -> Unit, // Update selectedImageUri di PostPage
    navController: NavController
) {
    val painter: Painter = painterResource(id = post.imageId)
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(
                onClick = { onImageClick(); onImageSelected(selectedImageUri) },
                modifier = Modifier
                    .padding(top = 8.dp)
                    .size(40.dp)
            ) {
                Icon(imageVector = Icons.Default.AddCircle, contentDescription = "Add")
            }
        }

        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Text(
                text = post.title,
                style = MaterialTheme.typography.h6,
                textAlign = TextAlign.Start,
            )

            TextField(
                value = post.description,
                onValueChange = { onDescriptionChanged(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                textStyle = MaterialTheme.typography.body1,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                singleLine = false,
            )

            Button(
                onClick = {
                    // Handle "Bagikan" button click
                    onImageSelected(selectedImageUri) // Kirim selectedImageUri ke PostPage
                    selectedImageUri?.let { uri ->
                        // Upload gambar ke Firebase Storage
                        val imageUrl = uploadGambarKeFirebaseStorage(uri)

                        // Simpan data ke Firebase Realtime Database
                        simpanDataKeDatabase(username, post.description, imageUrl, navController)
                    }
                },
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Bagikan")
            }
        }
    }
}


data class Post(
    @DrawableRes val imageId: Int,
    val title: String,
    val description: String,
)

fun simpanDataKeDatabase(username: String, caption: String, imageUrl: String, navController: NavController) {
    val database = Firebase.database
    val postinganRef: DatabaseReference = database.getReference("postingan")

    // Buat objek Postingan
    val postingan = Postingan(username, caption, imageUrl, timestamp = System.currentTimeMillis())

    // Simpan data ke Firebase Realtime Database
    val postinganId = postinganRef.push().key
    postinganId?.let {
        postinganRef.child(it).setValue(postingan)
    }
    navController.navigate("Posting")

}



fun uploadGambarKeFirebaseStorage(uri: Uri): String {
    val storage = Firebase.storage
    val storageRef = storage.reference

    // Buat referensi untuk gambar di Firebase Storage
    val imageRef = storageRef.child("images/${UUID.randomUUID()}.jpg")

    // Upload file ke Firebase Storage
    imageRef.putFile(uri)
        .addOnSuccessListener {
            // Handle pengungahan sukses
            // Dapatkan URL gambar
            imageRef.downloadUrl
                .addOnSuccessListener { downloadUri ->
                    // Gunakan URL gambar (misalnya, simpan di database)
                    val imageUrl = downloadUri.toString()
                    // Tambahkan logika atau penanganan sesuai kebutuhan Anda
                }
                .addOnFailureListener {
                    // Tangani jika gagal mendapatkan URL gambar
                }
        }
        .addOnFailureListener {
            // Tangani jika pengungahan gagal
        }

    return "" // Sesuaikan dengan kebutuhan Anda
}

