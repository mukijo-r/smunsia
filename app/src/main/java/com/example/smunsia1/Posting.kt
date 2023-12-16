package com.example.smunsia1

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.navigation.NavController


data class MenuOption(val text: String, val icon: ImageVector, val onClick: (NavController) -> Unit)

val menuOptions = listOf(
    MenuOption("New Post", Icons.Filled.Add) { navController -> navController.navigate("NewPost") },
    MenuOption("New Group", Icons.Filled.Person) { navController -> navController.navigate("NewGroup") },
    MenuOption("Profil", Icons.Filled.Settings) { navController -> navController.navigate("EditProfile") },
)

@Composable
fun ScreenPosting(navController: NavController) {
    val annotatedString = buildAnnotatedString {
        withStyle(style = SpanStyle(
            color = LocalContentColor.current,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )) {
            append("Irmayanti Juliana")
        }
    }

    var isMenuExpanded = false
    FloatingMenu(navController = navController) { isMenuExpanded = true }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
    ) {
        // Row pertama
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Kolom 1

            Spacer(modifier = Modifier.width(8.dp))

            // Kolom 2
            Text(
                text = "Postingan terbaru",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )
        }

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 18.dp, bottom = 8.dp),
            color = Color.Gray,
            thickness = 1.dp
        )

        // Row kedua
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Kolom 1: Avatar
            Avatar2()

            // Spacer
            Spacer(modifier = Modifier.width(8.dp))

            Column {
                ClickableText(
                    text = annotatedString,
                    onClick = {
                        // Navigate to the "Profile" route when clicked
                        navController.navigate("Profile")
                    },
                    modifier = Modifier.clickable { /* clickable modifier */ }
                )

                Text(
                    text = "a minute ago",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            // Spacer
            Spacer(modifier = Modifier.weight(1f))

            // Kolom 3: Menu 3 titik
            ThreeDotMenu()
        }

        // Row ketiga
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(text = "Tugas hari ini", fontSize = 16.sp)
        }

        // Row keempat (menggunakan Box untuk Image)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Column {
                Image(
                    painter = painterResource(id = R.drawable.post1),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(14f / 9f)
                )
            }
        }

        // Row kelima
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            LikeColumn()
            Spacer(modifier = Modifier.width(90.dp))
            CommentColumn()
            Spacer(modifier = Modifier.width(90.dp))
            ShareColumn()
        }

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 8.dp),
            color = Color.Gray,
            thickness = 3.dp
        )

        // Row keenam
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Kolom 1: Avatar
            Avatar()

            // Spacer
            Spacer(modifier = Modifier.width(8.dp))

            // Kolom 2: Nama Pengguna dan Waktu
            Column {
                Text(
                    text = "Mukijo",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "an hour ago",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            // Spacer
            Spacer(modifier = Modifier.weight(1f))

            // Kolom 3: Menu 3 titik
            ThreeDotMenu()
        }

        // Row ketujuh
        Row(
            modifier = Modifier
                .fillMaxWidth()
//                .padding(vertical = 80.dp)
        ) {
            Text(text = "Semangat", fontSize = 16.sp)
        }

        // Row keempat (menggunakan Box untuk Image)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 120.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.post2),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(14f / 9f)
            )
        }

        // Row kedelepan
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            LikeColumn()
            Spacer(modifier = Modifier.width(35.dp))
            CommentColumn()
            Spacer(modifier = Modifier.width(35.dp))
            ShareColumn()
        }

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 8.dp),
            color = Color.Gray,
            thickness = 3.dp
        )

    }
}

@Composable
fun icon3l() {
    val logoModifier = Modifier
        .size(40.dp)

    Image(
        painter = painterResource(id = R.drawable.icon3l),
        contentDescription = null,
        modifier = logoModifier
    )
}

@Composable
fun Avatar() {
    Image(
        painter = painterResource(id = R.drawable.ava1),
        contentDescription = null,
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.background)
    )
}

@Composable
fun Avatar2() {
    Image(
        painter = painterResource(id = R.drawable.ava2),
        contentDescription = null,
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.background)
    )
}

@Composable
fun ThreeDotMenu() {
    Icon(
        imageVector = Icons.Default.MoreVert,
        contentDescription = null,
        tint = MaterialTheme.colorScheme.primary,
        modifier = Modifier.size(24.dp)
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
    Text(
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

    Text(
        text = "10",
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Gray
    )
}

@Composable
fun ShareColumn() {

    Icon(
        imageVector = Icons.Default.Share,
        contentDescription = null,
        tint = MaterialTheme.colorScheme.primary,
        modifier = Modifier.size(24.dp)
    )

    Spacer(modifier = Modifier.width(8.dp))

    Text(
        text = "10",
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Gray
    )
}

@Composable
fun FloatingMenu(navController: NavController, onClickFab: () -> Unit) {
    var isMenuOpen by remember { mutableStateOf(false) }

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
                .zIndex(1f)
        ) {
            Text(text = "", fontSize = 12.sp)
            Divider(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp))

            menuOptions.forEach { option ->
                Button(
                    onClick = {
                        isMenuOpen = false
                        option.onClick.invoke(navController)
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
                        Text(text = option.text)
                    }
                }
            }
        }
    }
}





