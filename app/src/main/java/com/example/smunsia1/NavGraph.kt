package com.example.smunsia1

import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.smunsia1.ui.AuthViewModel
import com.example.smunsia1.ui.PostinganViewModel

@Composable
fun Nav(){
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()
    val postinganViewModel: PostinganViewModel = PostinganViewModel()
    NavHost(navController = navController, startDestination = "Login"){

        composable(route = "Login") { backStackEntry ->
            val usernameState = authViewModel.username.observeAsState(initial = "")
            val username = usernameState.value

            ScreenLogin(navController, authViewModel)
        }

        composable(route = "Register"){
            ScreenRegister(navController)
        }

        composable(route = "Profile/{username}") { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username")
            if (username != null) {
                ScreenProfile(navController, username)
            }
        }

        composable(route = "Posting") {
            ScreenPosting(navController, authViewModel, postinganViewModel)
        }

        composable(route = "Message"){
            ScreenMessage(navController)
        }

        composable(route = "NewPost/{username}") { backStackEntry ->
            val usernameState = authViewModel.username.observeAsState(initial = "")
            val username = usernameState.value

            PostPage(navController, authViewModel, username)
        }

        composable(route = "NewGroup/{username}") { backStackEntry ->
            val usernameState = authViewModel.username.observeAsState(initial = "")
            val username = usernameState.value

            ScreenGroup(navController, authViewModel, username)
        }

        composable(route = "GroupList/{username}") { backStackEntry ->
            val usernameState = authViewModel.username.observeAsState(initial = "")
            val username = usernameState.value

            GroupList(navController, authViewModel, username)
        }

        composable(route = "EditProfile/{username}") { backStackEntry ->
            val usernameState = authViewModel.username.observeAsState(initial = "")
            val username = usernameState.value

            ScreenEditProfile(navController, authViewModel, username)
        }

    }
}



