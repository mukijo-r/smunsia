package com.example.smunsia1

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.smunsia1.ui.theme.Smunsia1Theme


@Composable
fun PostPage(navController: NavHostController) {
    var descriptionText by remember { mutableStateOf("") }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tambahkan postingan") },
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
                    PostItem(post = post, onDescriptionChanged = { descriptionText = it })
                }
            }
        )
    }
}

@Composable
fun PostItem(post: Post, onDescriptionChanged: (String) -> Unit) {
    val painter: Painter = painterResource(id = post.imageId)

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
        Column {
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier.size(80.dp),
                contentScale = ContentScale.Crop
            )

            Column {
                IconButton(
                    onClick = { /* Handle icon button click */ },
                    modifier = Modifier
                        .padding(start = 20.dp, top = 8.dp)
                        .size(40.dp)
                ) {
                    Icon(imageVector = Icons.Default.AddCircle, contentDescription = "Add")
                }
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
                singleLine = false, // Set to true for a single-line input, false for multiline
            )

            Button(
                onClick = { /* Handle "Bagikan" button click */ },
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


