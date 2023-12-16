package com.example.smunsia1

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun ScreenLogin(navController: NavHostController) {
    var email by remember { mutableStateOf(TextFieldValue("army@gmail.com")) }
    var password by remember { mutableStateOf(TextFieldValue("*****")) }


    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Logo1()
        Spacer(modifier = Modifier.height(8.dp))
        HeaderText(text = "Login")
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it},
            label = { Text(text = stringResource(id = R.string.label_email_address))},
            placeholder = { Text(text = "")},
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
fun HeaderText(text: String) {
    Text(
        text = text,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
    )
}





