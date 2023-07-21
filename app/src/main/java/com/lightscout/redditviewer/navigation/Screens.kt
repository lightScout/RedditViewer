package com.lightscout.redditviewer.navigation

sealed class Screens(val route: String) {
    object HomeScreen : Screens("home")
    object DetailScreen : Screens("detail/{postId}")
}