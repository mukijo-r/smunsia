package com.example.smunsia1

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

data class Contact(val id: Int, val name: String, val avatar: Painter)

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ScreenGroup(navController: NavHostController) {
    var descriptionText by remember { mutableStateOf("Nama Group") }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Buat Group baru") },
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
                        Kelompok(
                            imageId = R.drawable.camera,
                            description = descriptionText,
                        ),
                    )
                ) { kelompok ->
                    CreateGroup(kelompok = kelompok, onDescriptionChanged = { descriptionText = it })
                }
            }
        )
    }
}

@Composable
fun CreateGroup(kelompok: Kelompok, onDescriptionChanged: (String) -> Unit) {
    val painter: Painter = painterResource(id = kelompok.imageId)

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {

        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            TextField(
                value = kelompok.description,
                onValueChange = { onDescriptionChanged(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                textStyle = MaterialTheme.typography.body1,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                singleLine = true,
            )
            Spacer(modifier = Modifier.size(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "Tambahkan foto (opsional)")
            }
            Spacer(modifier = Modifier.size(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Center,
            ) {
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier
                        .size(75.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.size(12.dp))
            Text(
                text = "Pilih kontak ",
                style = MaterialTheme.typography.h5,
                textAlign = TextAlign.Start,
            )

            //Kontak 1
            Spacer(modifier = Modifier.size(16.dp))
            Row {
                Ava2()
                Spacer(modifier = Modifier.width(8.dp))

                androidx.compose.material3.Text(
                    text = "Alice in Wonderland",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.weight(1f))
                Centang()
            }
            //Kontak 2
            Spacer(modifier = Modifier.size(16.dp))
            Row {
                Ava5()
                Spacer(modifier = Modifier.width(8.dp))

                androidx.compose.material3.Text(
                    text = "Annabelle",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.weight(1f))
                //Centang()
            }

            //Kontak 3
            Spacer(modifier = Modifier.size(16.dp))
            Row {
                Ava3()
                Spacer(modifier = Modifier.width(8.dp))

                androidx.compose.material3.Text(
                    text = "Bryan Adams",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.weight(1f))
                Centang()
            }

            //Kontak 4
            Spacer(modifier = Modifier.size(16.dp))
            Row {
                Ava4()
                Spacer(modifier = Modifier.width(8.dp))

                androidx.compose.material3.Text(
                    text = "Drake Johnson",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.weight(1f))
                //Centang()
            }

            //Kontak 5
            Spacer(modifier = Modifier.size(16.dp))
            Row {
                Ava1()
                Spacer(modifier = Modifier.width(8.dp))

                androidx.compose.material3.Text(
                    text = "Edward Chen",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.weight(1f))
                //Centang()
            }

            //Kontak 6
            Spacer(modifier = Modifier.size(16.dp))
            Row {
                Ava5()
                Spacer(modifier = Modifier.width(8.dp))

                androidx.compose.material3.Text(
                    text = "Fish Mooney",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.weight(1f))
                Centang()
            }

            //Kontak 7
            Spacer(modifier = Modifier.size(16.dp))
            Row {
                Ava3()
                Spacer(modifier = Modifier.width(8.dp))

                androidx.compose.material3.Text(
                    text = "Greg Steward",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.weight(1f))
                Centang()
            }

            //Kontak 8
            Spacer(modifier = Modifier.size(16.dp))
            Row {
                Ava2()
                Spacer(modifier = Modifier.width(8.dp))

                androidx.compose.material3.Text(
                    text = "Hanna Barbara",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.weight(1f))
                //Centang()
            }

            Button(
                onClick = { /* Handle "Bagikan" button click */ },
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Buat Group")
            }

        }
    }
}

data class Kelompok(
    @DrawableRes val imageId: Int,
    val description: String,
)

@Composable
fun Ava1() {
    Image(
        painter = painterResource(id = R.drawable.ava1),
        contentDescription = null,
        modifier = Modifier
            .size(30.dp)
            .clip(CircleShape)
            .background(androidx.compose.material3.MaterialTheme.colorScheme.background)
    )
}

@Composable
fun Ava2() {
    Image(
        painter = painterResource(id = R.drawable.ava2),
        contentDescription = null,
        modifier = Modifier
            .size(30.dp)
            .clip(CircleShape)
            .background(androidx.compose.material3.MaterialTheme.colorScheme.background)
    )
}

@Composable
fun Ava3() {
    Image(
        painter = painterResource(id = R.drawable.ava3),
        contentDescription = null,
        modifier = Modifier
            .size(30.dp)
            .clip(CircleShape)
            .background(androidx.compose.material3.MaterialTheme.colorScheme.background)
    )
}

@Composable
fun Ava4() {
    Image(
        painter = painterResource(id = R.drawable.ava4),
        contentDescription = null,
        modifier = Modifier
            .size(30.dp)
            .clip(CircleShape)
            .background(androidx.compose.material3.MaterialTheme.colorScheme.background)
    )
}

@Composable
fun Ava5() {
    Image(
        painter = painterResource(id = R.drawable.ava5),
        contentDescription = null,
        modifier = Modifier
            .size(30.dp)
            .clip(CircleShape)
            .background(androidx.compose.material3.MaterialTheme.colorScheme.background)
    )
}

@Composable
fun Centang() {
    Icon(
        imageVector = Icons.Default.Check,
        contentDescription = null,
        tint = androidx.compose.material3.MaterialTheme.colorScheme.primary,
        modifier = Modifier.size(24.dp)
    )
}

