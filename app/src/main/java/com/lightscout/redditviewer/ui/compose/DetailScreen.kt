package com.lightscout.redditviewer.ui.compose

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun DetailScreen(navController: NavController) {
    Surface(color = MaterialTheme.colors.background) {
        Text(text = "Detail Screen")
    }
}