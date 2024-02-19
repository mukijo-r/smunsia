package com.example.smunsia1

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.smunsia1.ui.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenLogin(navController: NavHostController, authViewModel: AuthViewModel) {
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var showMessage by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }

    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isLandscape) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // content for landscape mode
                Logo1()
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    HeaderText(text = "Login")
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text(text = stringResource(id = R.string.label_email_address)) },
                        placeholder = { Text(text = "") },
                        shape = RoundedCornerShape(8.dp),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = "Email Icon"
                            )
                        }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text(text = stringResource(id = R.string.label_password)) },
                        placeholder = { Text(text = stringResource(id = R.string.hint_enter_password)) },
                        shape = RoundedCornerShape(8.dp),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Email Icon"
                            )
                        }
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Button(onClick = {
                        navController.navigate("Posting")
                    }) {
                        Text(text = "Login", fontSize = 20.sp)
                    }
                }
            }
        } else {
            // content for portrait mode
            Logo1()
            Spacer(modifier = Modifier.height(8.dp))
            HeaderText(text = "Login")
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(text = stringResource(id = R.string.label_email_address)) },
                placeholder = { Text(text = "") },
                shape = RoundedCornerShape(8.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Email Icon"
                    )
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(text = stringResource(id = R.string.label_password)) },
                placeholder = { Text(text = stringResource(id = R.string.hint_enter_password)) },
                shape = RoundedCornerShape(8.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Email Icon"
                    )
                }
            )
            Spacer(modifier = Modifier.height(15.dp))
            Button(onClick = {
                // Validasi data pengguna sebelum menyimpan ke database
                if (email.text.isNotEmpty() && password.text.isNotEmpty()) {
                    // Menggunakan Firebase Authentication untuk otentikasi
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(
                        email.text, password.text
                    ).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user = task.result?.user
                            val userUid = user?.uid

                            // Ambil data dari Realtime Database
                            if (userUid != null) {
                                val databaseReference = FirebaseDatabase.getInstance().getReference("users")
                                databaseReference.child(userUid).addListenerForSingleValueEvent(object :
                                    ValueEventListener {
                                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                                        val username = dataSnapshot.child("username").getValue(String::class.java)
                                        authViewModel.setLoggedInUser(username ?: "")
                                    }

                                    override fun onCancelled(databaseError: DatabaseError) {

                                    }
                                })
                            }
                            // Lanjutkan navigasi
                            navController.navigate("Posting")
                        } else {
                            // Jika otentikasi gagal, tampilkan pesan kesalahan
                            showMessage = true
                            message = "Email atau password salah"
                        }
                    }
                } else {
                    // Menampilkan pesan kesalahan jika data tidak lengkap
                    showMessage = true
                    message = "Harap isi semua kolom dengan benar"
                }
            }) {
                Text(text = "Login", fontSize = 20.sp)
            }
        }

        if (showMessage) {
            LaunchedEffect(showMessage) {
                delay(2000L)
                showMessage = false
            }

            Snackbar(
                modifier = Modifier.padding(8.dp),
                action = {
                    TextButton(onClick = { showMessage = false }) {
                        Text("")
                    }
                }
            ) {
                Text(message)
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        Row(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .clickable {
                    navController.navigate("Register")
                }
        ) {
            val annotatedString = buildAnnotatedString {
                append("Belum memiliki akun? ")
                withStyle(style = SpanStyle(color = Color.Green)) {
                    append("Register")
                }
            }

            ClickableText(
                text = annotatedString,
                onClick = {
                    navController.navigate("Register")
                },
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(15.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = "Powered by", fontSize = 20.sp, modifier = Modifier.padding(end = 8.dp))
            Logo2()
        }
    }
}


@Composable
fun Logo1() {
    val logoModifier = Modifier
        .size(150.dp)
        .padding(8.dp)

    Image(
        painter = painterResource(id = R.drawable.logo),
        contentDescription = null,
        modifier = logoModifier
    )
}

@Composable
fun Logo2() {
    val logoModifier = Modifier
        .size(100.dp)

    Image(
        painter = painterResource(id = R.drawable.unsia),
        contentDescription = null,
        modifier = logoModifier
    )
}

@Composable
fun HeaderText(text: String) {
    Text(
        text = text,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
    )
}







