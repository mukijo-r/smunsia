package com.example.smunsia1

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.delay


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenRegister(navController: NavHostController) {
    var username by remember { mutableStateOf(TextFieldValue("")) }
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password1 by remember { mutableStateOf(TextFieldValue("")) }
    var password2 by remember { mutableStateOf(TextFieldValue("")) }
    var showMessage by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }

    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Logo1()
        Spacer(modifier = Modifier.height(8.dp))
        HeaderText(text = "Register")

        if (isLandscape) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    OutlinedTextField(
                        value = username,
                        onValueChange = { username = it },
                        label = { Text(text = stringResource(id = R.string.label_username)) },
                        placeholder = { Text(text = "") },
                        shape = RoundedCornerShape(8.dp),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Person Icon"
                            )
                        }
                    )
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
                }

                Column {
                    OutlinedTextField(
                        value = password1,
                        onValueChange = { password1 = it },
                        label = { Text(text = stringResource(id = R.string.label_password)) },
                        placeholder = { Text(text = stringResource(id = R.string.hint_enter_password)) },
                        shape = RoundedCornerShape(8.dp),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Lock Icon"
                            )
                        }
                    )
                    OutlinedTextField(
                        value = password2,
                        onValueChange = { password2 = it },
                        label = { Text(text = stringResource(id = R.string.label_password)) },
                        placeholder = { Text(text = stringResource(id = R.string.hint_enter_password)) },
                        shape = RoundedCornerShape(8.dp),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Lock Icon"
                            )
                        }
                    )
                }
            }
        } else {
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text(text = stringResource(id = R.string.label_username)) },
                placeholder = { Text(text = "") },
                shape = RoundedCornerShape(8.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Person Icon"
                    )
                }
            )
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
            OutlinedTextField(
                value = password1,
                onValueChange = { password1 = it },
                label = { Text(text = stringResource(id = R.string.label_password)) },
                placeholder = { Text(text = stringResource(id = R.string.hint_enter_password)) },
                shape = RoundedCornerShape(8.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Lock Icon"
                    )
                }
            )
            OutlinedTextField(
                value = password2,
                onValueChange = { password2 = it },
                label = { Text(text = stringResource(id = R.string.label_password)) },
                placeholder = { Text(text = stringResource(id = R.string.hint_enter_password)) },
                shape = RoundedCornerShape(8.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Lock Icon"
                    )
                }
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = "*) password minimal 6 karakter", fontSize = 12.sp, modifier = Modifier.padding(end = 8.dp))
        }

        Spacer(modifier = Modifier.height(25.dp))
        Button(onClick = {
            // Validasi data pengguna sebelum menyimpan ke database
            if (username.text.isNotEmpty() && email.text.isNotEmpty() && password1.text.isNotEmpty() && password1.text == password2.text) {
                // Menggunakan Firebase Authentication untuk registrasi
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    email.text.toString(),
                    password1.text.toString()
                ).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Registrasi berhasil
                        val userUid = FirebaseAuth.getInstance().currentUser?.uid
                        saveUserDataToDatabase(userUid, User(username = username.text, email = email.text))
                        showMessage = true
                        message = "Registrasi berhasil"

                        // Mengosongkan nilai TextField setelah registrasi berhasil
                        username = TextFieldValue("")
                        email = TextFieldValue("")
                        password1 = TextFieldValue("")
                        password2 = TextFieldValue("")
                    } else {
                        // Registrasi gagal, tampilkan pesan kesalahan
                        showMessage = true
                        message = "Registrasi gagal: ${task.exception?.message}"
                    }
                }
            } else {
                // Tampilkan pesan kesalahan jika data tidak lengkap
                showMessage = true
                message = "Harap isi semua kolom dengan benar"
            }
        }) {
            Text(text = "Register", fontSize = 20.sp)
        }


        if (showMessage) {
            LaunchedEffect(showMessage) {
                delay(3000L)
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


        Spacer(modifier = Modifier.height(25.dp))
        Row(

        ) {
            val annotatedString = buildAnnotatedString {
                append("Sudah memiliki akun? ")
                withStyle(style = SpanStyle(color = Color.Green)) {
                    append("Login")
                }
            }

            ClickableText(
                text = annotatedString,
                onClick = {
                    navController.navigate("Login")
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

data class User(
    val username: String = "",
    val email: String = "",
    val password: String = ""
)

private fun saveUserDataToDatabase(userUid: String?, user: User) {
    if (userUid != null) {
        val databaseReference = FirebaseDatabase.getInstance().getReference("users")
        databaseReference.child(userUid).setValue(
            mapOf(
                "username" to user.username,
                "email" to user.email
            )
        )
    }
}

