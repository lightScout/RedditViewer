package com.lightscout.redditviewer.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.lightscout.redditviewer.navigation.NavHostGraph
import com.lightscout.redditviewer.ui.theme.RedditViewerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            RedditViewerTheme {
                NavHostGraph(navController = navController)
            }
        }
    }
}