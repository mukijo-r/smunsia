package com.example.smunsia1

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenProfile(navController: NavHostController) {
    val annotatedString = buildAnnotatedString {
        withStyle(style = SpanStyle(
            color = LocalContentColor.current,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        ) {
            append("Irmayanti Juliana")
        }
    }

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
            Image(
                painter = painterResource(id = R.drawable.ava2),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
            )
        }
        Text(text =  "Irmayanti Juliana", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(25.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            UserStat(icon = Icons.Default.Create, count = 150, label = "Posts")
            UserStat(icon = Icons.Default.Person, count = 1000, label = "Followers")
            UserStat(icon = Icons.Default.Person, count = 500, label = "Following")
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

                // Kolom 2: Nama Pengguna dan Waktu
                Column {
                    // Teks yang dapat diklik
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
                Image(
                    painter = painterResource(id = R.drawable.post1),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(14f / 9f)
                )
            }

            // Row kelima
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                LikeColumn()
                Spacer(modifier = Modifier.width(100.dp))
                CommentColumn()
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
