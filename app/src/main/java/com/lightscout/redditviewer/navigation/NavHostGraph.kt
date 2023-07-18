package com.lightscout.redditviewer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.lightscout.redditviewer.ui.compose.DetailScreen
import com.lightscout.redditviewer.ui.compose.PostsContainer

@Composable
fun NavHostGraph (navController: NavHostController){
    NavHost(navController = navController, startDestination = Screens.HomeScreen.route){
        composable(route = Screens.HomeScreen.route){
            PostsContainer(navController = navController)
        }
        composable(route = Screens.DetailScreen.route){
            DetailScreen(navController = navController)
        }
    }

}