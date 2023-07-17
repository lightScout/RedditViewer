package com.lightscout.redditviewer.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.lightscout.redditviewer.ui.compose.PostsContainer
import com.lightscout.redditviewer.ui.theme.RedditViewerTheme
import com.lightscout.redditviewer.util.Common
import com.lightscout.redditviewer.viewmodel.RedditViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModel: RedditViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Common().isNetworkAvailable(this).not())
            viewModel.fromCache()
        else
            viewModel.getPosts()

        setContent {
            RedditViewerTheme {
                PostsContainer(viewModel)
            }
        }
    }
}