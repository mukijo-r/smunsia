package com.example.smunsia1

import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.smunsia1.ui.AuthViewModel
import com.example.smunsia1.ui.PostinganViewModel
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlin.random.Random
import androidx.compose.material3.Text as Text1


data class MenuOption(val text: String, val icon: ImageVector, val onClick: (NavController, String) -> Unit)
val database = Firebase.database
val postinganRef = database.getReference("postingan")

val menuOptions = listOf(
    MenuOption("New Post", Icons.Filled.Add) { navController, username -> navController.navigate("NewPost/$username") },
    MenuOption("New Group", Icons.Filled.Person) { navController, username -> navController.navigate("NewGroup/$username") },
    MenuOption("Profil", Icons.Filled.Settings) { navController, username -> navController.navigate("EditProfile/$username") },
    MenuOption("Logout", Icons.Filled.Home) { navController, username -> navController.navigate("Login") },
)

@Composable
fun ScreenPosting(
    navController: NavController,
    authViewModel: AuthViewModel,
    postinganViewModel: PostinganViewModel = viewModel()
) {
    val postingans = postinganViewModel.postingans.value ?: emptyList()

    var isMenuExpanded = false

    FloatingMenu(
        navController = navController,
        authViewModel = authViewModel
    ) { isMenuExpanded = true }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Spacer(modifier = Modifier.width(8.dp))
            Text1(
                text = "Postingan terbaru",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 8.dp),
            color = Color.Gray,
            thickness = 1.dp
        )

        // Content
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(10f),
            content = {
                items(postingans) { postingan ->
                    PostingItem(postingan = postingan, navController = navController)
                }
            }
        )
    }
}

@Composable
fun PostingItem(postingan: Postingan, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Avatar
                Avatar()

                Spacer(modifier = Modifier.width(8.dp))

                // User Info (Username and Timestamp)
                Column {
                    ClickableText(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(
                                color = LocalContentColor.current,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                            ) {
                                append(postingan.username)
                            }
                        },
                        onClick = {
                            navController.navigate("Profile/${postingan.username}")
                        },
                        modifier = Modifier.clickable { }
                    )

                    val formattedTimestamp = formatDate(postingan.timestamp)
                    Text1(
                        text = "$formattedTimestamp",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }

            // Caption
            Text1(text = "${postingan.caption}")

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Column {
                    ImagePost()
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CommentColumn()
                Spacer(modifier = Modifier.width(120.dp))
                LikeColumn()
            }

        }
    }
}


@Composable
fun Avatar() {
    val randomAvaIndex = remember { mutableStateOf(Random.nextInt(1, 6)) }

    val avatarResourceId = when (randomAvaIndex.value) {
        1 -> R.drawable.ava1
        2 -> R.drawable.ava2
        3 -> R.drawable.ava3
        4 -> R.drawable.ava4
        5 -> R.drawable.ava5
        else -> R.drawable.ava1 // Default jika indeks acak tidak sesuai dengan yang diharapkan
    }

    Image(
        painter = painterResource(id = avatarResourceId),
        contentDescription = null,
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.background)
    )
}

@Composable
fun LikeColumn() {

    Icon(
        imageVector = Icons.Default.ThumbUp,
        contentDescription = null,
        tint = MaterialTheme.colorScheme.primary,
        modifier = Modifier.size(20.dp)
    )
    Spacer(modifier = Modifier.width(8.dp))

    // Teks "likes"
    Text1(
        text = "21",
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Gray
    )
}

@Composable
fun CommentColumn() {

    Icon(
        imageVector = Icons.Default.Send,
        contentDescription = null,
        tint = MaterialTheme.colorScheme.primary,
        modifier = Modifier.size(24.dp)
    )

    Spacer(modifier = Modifier.width(8.dp))

    Text1(
        text = "Komentar",
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Gray
    )
}

fun formatDate(timestamp: Long): String {
    val date = java.util.Date(timestamp)
    val format = SimpleDateFormat("dd-MM-yyyy HH:mm")
    return format.format(date)
}

@Composable
fun FloatingMenu(navController: NavController, authViewModel: AuthViewModel, onClickFab: () -> Unit) {
    var isMenuOpen by remember { mutableStateOf(false) }
    val username by authViewModel.username.observeAsState("")

    FloatingActionButton(
        onClick = { isMenuOpen = !isMenuOpen },
        modifier = Modifier
            .padding(8.dp)
    ) {
        Icon(imageVector = Icons.Default.Menu, contentDescription = null)
    }

    if (isMenuOpen) {
        Column(
            modifier = Modifier
                .background(Color.Transparent.copy(alpha = 0.5f))
                .fillMaxSize()
                .clickable { isMenuOpen = false }
                .padding(16.dp)
                .zIndex(4f)
        ) {
            Text1(text = "", fontSize = 12.sp)
            Divider(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp))

            menuOptions.forEach { option ->
                Button(
                    onClick = {
                        isMenuOpen = false
                        option.onClick.invoke(navController, username ?: "")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.Start)
                        .padding(bottom = 8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(imageVector = option.icon, contentDescription = null, modifier = Modifier.size(24.dp))
                        Text1(text = option.text)
                    }
                }
            }
        }
    }
}

@Composable
fun ImagePost() {
    val randomImageIndex = remember { mutableStateOf(Random.nextInt(1, 11)) }

    val imagePostResourceId = when (randomImageIndex.value) {
        1 -> R.drawable.post1
        2 -> R.drawable.post2
        3 -> R.drawable.post3
        4 -> R.drawable.post4
        5 -> R.drawable.post5
        6 -> R.drawable.post6
        7 -> R.drawable.post7
        8 -> R.drawable.post8
        9 -> R.drawable.post9
        10 -> R.drawable.post10
        else -> R.drawable.post1
    }

    Image(
        painter = painterResource(id = imagePostResourceId),
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(14f / 9f)
    )
}






