package com.example.smunsia1

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

data class Message(val sender: String, val content: String)


private val Any.content: Any
    get() {
        TODO("Not yet implemented")
    }
private val Any.sender: Any
    get() {
        TODO("Not yet implemented")
    }

@Composable
fun ScreenMessage(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Now, integrate DirectMessageScreen
        DirectMessageScreen(navController = navController, messages = getDummyMessages())
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DirectMessageScreen(navController: NavHostController, messages: List<Message>) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Irmayanti Juliana") },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            // Navigate back
                            navController.popBackStack()
                        }
                    ) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // List of Direct Messages
                LazyColumn(
                    modifier = Modifier
                        .weight(1f),
                    contentPadding = PaddingValues(vertical = 40.dp)
                ) {
                    items(messages) { message ->
                        DirectMessageItem(message = message)
                    }
                }

                // Compose Message Section
                ComposeMessageSection(
                    onMessageSent = { newMessage ->
                        // Handle sending the new message
                    }
                )
            }
        }
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ComposeMessageSection(onMessageSent: (String) -> Unit) {
    // LocalFocusManager is used to manage focus within the Compose hierarchy
    val focusManager = LocalFocusManager.current
    // LocalSoftwareKeyboardController is used to control the software keyboard
    val keyboardController = LocalSoftwareKeyboardController.current

    // Input text state
    var messageText by remember { mutableStateOf("") }

    // Send button click handler
    val onSendClick: () -> Unit = {
        if (messageText.isNotEmpty()) {
            onMessageSent(messageText)
            messageText = "" // Clear the input field after sending
            focusManager.clearFocus()
        }
    }

    // Message input section
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Message input field
        BasicTextField(
            value = messageText,
            onValueChange = { messageText = it },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = androidx.compose.ui.text.input.ImeAction.Send
            ),
            keyboardActions = KeyboardActions(
                onSend = {
                    onSendClick()
                    keyboardController?.hide()
                }
            ),
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
        )

        // Send button
        IconButton(
            onClick = onSendClick,
            modifier = Modifier
                .size(40.dp)
                .background(MaterialTheme.colorScheme.primary)
                .clip(CircleShape)
        ) {
            Icon(imageVector = Icons.Default.Send, contentDescription = "Back")
        }
    }
}

@Composable
fun DirectMessageItem(message: Message) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        if (message.sender == "Irmayanti Juliana") {
            Image(
                painter = painterResource(id = R.drawable.ava2),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
            )

            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .background(MaterialTheme.colorScheme.background)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable {
                        // Handle clicking on a message (optional)
                    }
                    .fillMaxWidth()
            ) {
                Text(text = message.sender, textAlign = TextAlign.Start, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = message.content, textAlign = TextAlign.Start)
            }
        } else {
            Image(
                painter = painterResource(id = R.drawable.ava1),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
            )
            // Isi pesan pengirim
            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .background(MaterialTheme.colorScheme.background)
                    .clip(RoundedCornerShape(10.dp))
                    .fillMaxWidth()
            ) {
                Text(text = "You", textAlign = TextAlign.End, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = message.content, textAlign = TextAlign.End)
            }
        }
    }
}


@Composable
fun getDummyMessages(): List<Message> {
    // Replace this function with actual data retrieval logic
    return listOf(
        Message(sender = "You", content = "Pagi"),
        Message(sender = "Irmayanti Juliana", content = "Pagi juga..."),
        Message(sender = "You", content = "Gimana progres tugas?"),
        Message(sender = "Irmayanti Juliana", content = "Aman...")
    )
}

