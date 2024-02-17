package com.example.smunsia1

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.smunsia1.ui.PostinganViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenProfile(navController: NavHostController, username: String, postinganViewModel: PostinganViewModel = viewModel()) {
    val annotatedString = buildAnnotatedString {
        withStyle(style = SpanStyle(
            color = LocalContentColor.current,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        ) {
            append(username)
        }
    }

    val postingans by postinganViewModel.postingans.observeAsState(emptyList())
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBar(
            title = {
            },
            navigationIcon = {
                IconButton(
                    onClick = {
                        // Navigate back to "Posting" route
                        navController.popBackStack()
                    }
                ) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }
            }
        )

        Surface(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
        ) {
            // Use painterResource for image from res/drawable
            Avatar()
        }
        Text(text =  "$username", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(25.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            UserStat(icon = Icons.Default.Create, count = 1, label = "Posts")
            UserStat(icon = Icons.Default.Person, count = 0, label = "Followers")
            UserStat(icon = Icons.Default.Person, count = 0, label = "Following")
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            FollowButton()

            // Button Pesan
            OutlinedButton(
                onClick = { navController.navigate("Message") },
                border = BorderStroke(1.dp, Color.DarkGray),
                modifier = Modifier.padding(8.dp),
                shape = MaterialTheme.shapes.small
            ) {
                Icon(
                    imageVector = Icons.Default.MailOutline,
                    contentDescription = null,
                    modifier = Modifier.padding(end = 6.dp)
                )
                Text(text = "Pesan")
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        ) {
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 8.dp),
                color = Color.Gray,
                thickness = 1.dp
            )
            val filteredPostingans = postingans.filter { it.username == username }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                content = {
                    items(filteredPostingans) { postingan ->
                        PostingItem(postingan = postingan, navController = navController)
                    }
                }
            )

        }

    }
}

@Composable
fun UserStat(icon: ImageVector, count: Int, label: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable {
            // Handle click on stats
        }
    ) {
        Icon(imageVector = icon, contentDescription = null)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = count.toString(), fontWeight = FontWeight.Bold)
        Text(text = label, fontSize = 12.sp)
    }
}

@Composable
fun FollowButton() {
    var isFollowing by remember { mutableStateOf(false) }

    OutlinedButton(
        onClick = {
            isFollowing = !isFollowing
        },
        border = BorderStroke(1.dp, if (isFollowing) Color.DarkGray else Color.Blue),
        modifier = Modifier.padding(8.dp),
        shape = MaterialTheme.shapes.small
    ) {
        if (isFollowing) {
            Icon(
                imageVector = Icons.Default.AddCircle,
                contentDescription = null,
                modifier = Modifier.padding(end = 6.dp)
            )
            Text(text = "Ikuti")
        } else {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                modifier = Modifier.padding(end = 6.dp)
            )
            Text(text = "Mengikuti")
        }
    }
}






