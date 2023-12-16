package com.example.smunsia1

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Nav(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "Login"){

        composable(route = "Login"){
            ScreenLogin(navController)
        }
        composable(route = "Register"){
            ScreenRegister(navController)
        }
        composable(route = "Profile"){
            ScreenProfile(navController)
        }
        composable(route = "Posting"){
            ScreenPosting(navController)
        }
        composable(route = "Message"){
            ScreenMessage(navController)
        }
        composable(route = "NewPost"){
            PostPage(navController)
        }
        composable(route = "NewGroup"){
            ScreenGroup(navController)
        }
        composable(route = "EditProfile"){
            ScreenEditProfile(navController)
        }
    }
}

