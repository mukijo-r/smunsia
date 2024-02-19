package com.example.smunsia1

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.data.UiToolingDataApi
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.smunsia1.ui.AuthViewModel
import com.example.smunsia1.ui.Group
import com.example.smunsia1.ui.GroupListViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(UiToolingDataApi::class, ExperimentalMaterial3Api::class)
@Composable
fun GroupList(navController: NavController, authViewModel: AuthViewModel, username: String) {
    val groupListViewModel: GroupListViewModel = viewModel()
    val groups by groupListViewModel.groups.observeAsState(emptyList())

    Scaffold(
        topBar = {
            androidx.compose.material.TopAppBar(
                title = { androidx.compose.material.Text("Daftar Group") },
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
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 60.dp)
                ) {
                    items(groups) { group ->
                        GroupListItem(group = group, navController = navController)
                        Divider()
                    }
                }
            }
        }
    }
}

@Composable
fun GroupListItem(group: Group, navController: NavController) {
    // Implement the UI for each group item
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { /* Handle group item click, navigate to group details, etc. */ }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = group.groupName, fontWeight = FontWeight.Bold)
            Text(text = group.description)
            // Add other group details as needed
        }
    }
}
