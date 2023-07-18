package com.lightscout.redditviewer.navigation

sealed class Screens(val route: String, val name: String) {
    object HomeScreen : Screens("home_screen", "Home")
    object DetailScreen : Screens("detail_screen", "Detail")
}