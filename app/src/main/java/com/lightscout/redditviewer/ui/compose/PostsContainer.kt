package com.lightscout.redditviewer.ui.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.lightscout.redditviewer.model.data.Post
import com.lightscout.redditviewer.viewmodel.RedditViewModel
import com.lightscout.redditviewer.viewmodel.ViewModelState

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PostsContainer(viewModel: RedditViewModel) {
    val state by viewModel.state.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val pullRefreshState = rememberPullRefreshState(isRefreshing, { viewModel.refresh() })
    when (state) {
        is ViewModelState.Loading -> {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                CircularProgressIndicator(modifier = Modifier.size(48.dp))
            }
        }

        is ViewModelState.Error -> {
            Text(
                text = (state as ViewModelState.Error).message,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.primary
            )
        }

        is ViewModelState.Success -> {
            AnimatedVisibility(visible = isRefreshing.not(), enter = fadeIn(), exit = fadeOut()) {
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                        .pullRefresh(pullRefreshState)
                ) {
                    items(
                        items = if (state is ViewModelState.Success) (state as ViewModelState.Success).data else listOf(),
                        itemContent = {
                            PostItem(post = it)
                        })
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                PullRefreshIndicator(
                    isRefreshing,
                    pullRefreshState,

                    )
            }

        }
    }


}

@Preview
@Composable
fun PostItem(post: Post = Post("Title", "")) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        PostImage(post = post)
        PostTitle(post = post)
    }
}

@Preview
@Composable
fun PostList(post: List<Post> = listOf(Post("Title", ""), Post("Title", ""))) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        items(items = post, itemContent = {
            PostItem(post = it)
        })
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

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PostImage(post: Post = Post("Title", "")) {
    GlideImage(
        model = post.imageUrl,
        contentDescription = "Post Image",
        modifier = Modifier.size(100.dp)
    )
}
