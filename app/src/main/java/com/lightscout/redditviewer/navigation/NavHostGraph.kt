package com.lightscout.redditviewer.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.lightscout.redditviewer.ui.compose.DetailScreen
import com.lightscout.redditviewer.ui.compose.PostsContainer
import com.lightscout.redditviewer.util.Constants.Companion.POST_ID
import com.lightscout.redditviewer.viewmodel.RedditViewModel

@Composable
fun NavHostGraph(navController: NavHostController) {
    val viewModel = hiltViewModel<RedditViewModel>()

    NavHost(navController = navController, startDestination = Screens.HomeScreen.route) {
        composable(route = Screens.HomeScreen.route) {
            PostsContainer(navController = navController, viewModel)
        }
        composable(route = Screens.DetailScreen.route) {

            DetailScreen(
                it.arguments?.getString(POST_ID) ?: "",
                viewModel
            )

        }
    }

}