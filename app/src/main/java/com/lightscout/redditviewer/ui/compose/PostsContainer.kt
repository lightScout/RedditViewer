package com.lightscout.redditviewer.ui.compose

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lightscout.redditviewer.model.data.Post
import com.lightscout.redditviewer.viewmodel.RedditViewModel
import com.lightscout.redditviewer.viewmodel.ViewModelState

@Composable
fun PostsContainer(viewModel: RedditViewModel) {
    val state by viewModel.state.collectAsState()
//    AnimatedVisibility(visible = state is ViewModelState.Loading) {
//        CircularProgressIndicator(modifier = Modifier.size(48.dp))
//    }
    when (state) {
        is ViewModelState.Loading -> {
            CircularProgressIndicator(modifier = Modifier.size(48.dp))
        }
        is ViewModelState.Error -> {
            Text(
                text = (state as ViewModelState.Error).message,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.primary
            )
        }
        is ViewModelState.Success -> {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                items(items = (state as ViewModelState.Success).data) {
                    PostItem(post = it)
                }
            }
        }
    }


}

@Composable
fun PostItem(post: Post) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
//        PostImage(post = post)
        PostTitle(post = post)
    }
}

@Composable
fun PostTitle(post: Post) {
    Text(
        text = post.title,
        style = MaterialTheme.typography.body1,
        color = MaterialTheme.colors.primary
    )
}

@Composable
fun PostImage(post: Post) {

}
