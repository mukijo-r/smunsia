package com.example.smunsia1

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.smunsia1.ui.AuthViewModel
import com.example.smunsia1.ui.PostinganViewModel
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

data class Contact(val id: Int, val name: String, val avatar: Painter)

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ScreenGroup(navController: NavController, authViewModel: AuthViewModel, username: String, postinganViewModel: PostinganViewModel = viewModel()) {
    var nameText by remember { mutableStateOf("Nama Group") }
    var descriptionText by remember { mutableStateOf("Deskripsi Group") }
    val postingans = postinganViewModel.postingans.value ?: emptyList()
    val uniqueUsernames = postingans.map { it.username }.distinct()
    val selectedUsernames = remember { mutableStateListOf<String>() }

    val firebaseRepository = FirebaseRepository()

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
        Column {
            Column {
                TextField(
                    value = nameText,
                    onValueChange = { nameText = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                        .padding(bottom = 8.dp)
                        .padding(start = 8.dp)
                        .padding(end = 8.dp),
                    textStyle = MaterialTheme.typography.body1,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    singleLine = true,
                )
                TextField(
                    value = descriptionText,
                    onValueChange = { descriptionText = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                        .padding(bottom = 8.dp)
                        .padding(start = 8.dp)
                        .padding(end = 8.dp),
                    textStyle = MaterialTheme.typography.body1,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    singleLine = true,
                )
            }

            Text(
                text = "Pilih kontak ",
                style = MaterialTheme.typography.h6,
                textAlign = TextAlign.Start,
            )

            LazyColumn(
                modifier = Modifier
            ) {
                items(uniqueUsernames) { uniqueUsername ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = selectedUsernames.contains(uniqueUsername),
                            onCheckedChange = { isChecked ->
                                if (isChecked) {
                                    selectedUsernames.add(uniqueUsername)
                                } else {
                                    selectedUsernames.remove(uniqueUsername)
                                }
                            },
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(text = uniqueUsername, modifier = Modifier.weight(1f))
                    }
                }
            }

            Button(
                onClick = {
                    firebaseRepository.createGroup(nameText, descriptionText, selectedUsernames)
                    navController.navigate("GroupList")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Text(text = "Buat Group")
            }
        }
    }
}


data class Group(
    val groupId: String,
    val groupName: String,
    val description: String
)

data class GroupMember(
    val groupId: String,
    val userId: String
)

class FirebaseRepository {

    private val groupsRef = Firebase.database.reference.child("groups")
    private val groupMembersRef = Firebase.database.reference.child("groupMembers")

    fun createGroup(groupName: String, description: String, memberUserIds: List<String>) {
        val groupId = groupsRef.push().key // Generate a unique key for the group
        val group = Group(groupId!!, groupName, description)

        groupsRef.child(groupId).setValue(group)

        // Add group members
        memberUserIds.forEach { userId ->
            val groupMember = GroupMember(groupId, userId)
            groupMembersRef.child(groupId).child(userId).setValue(groupMember)
        }
    }
}


//@Composable
//fun CreateGroup(postingan: Postingan, navController: NavController) {
//    Text(
//        text = "${postingan.username}",
//        fontWeight = FontWeight.Bold,
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(8.dp) // Adjust the padding as needed
//    )
//}

