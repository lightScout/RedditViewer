package com.lightscout.redditviewer.ui.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.lightscout.redditviewer.model.data.Post
import com.lightscout.redditviewer.util.Common
import com.lightscout.redditviewer.viewmodel.RedditViewModel
import com.lightscout.redditviewer.viewmodel.ViewModelState
import kotlinx.coroutines.flow.collectLatest
import com.lightscout.redditviewer.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PostsContainer(navController: NavController, viewModel: RedditViewModel) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val pullRefreshState = rememberPullRefreshState(isRefreshing, {
        if (Common().isNetworkAvailable(context))
            viewModel.refresh()
        else
            viewModel.fromCache()

    })
    val listState = rememberLazyListState()
    val isOnline = Common().isNetworkAvailable(context)


    LaunchedEffect(viewModel) {
        if (isOnline.not())
            viewModel.fromCache()
        else
            viewModel.getPosts()
    }



    when (state) {
        is ViewModelState.Loading -> {
            LoadingView()
        }

        is ViewModelState.Error -> {
            ErrorView(pullRefreshState, isRefreshing)
        }

        is ViewModelState.Success -> {
            PostListOnlineView(
                isRefreshing,
                isLoading,
                pullRefreshState,
                state as ViewModelState.Success,
                listState,
                viewModel,
                navController
            )

        }

        is ViewModelState.Offline -> {
            PostListOfflineView(
                state as ViewModelState.Offline,
                navController,
                pullRefreshState,
                isRefreshing
            )
        }
    }


}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ErrorView(
    pullRefreshState: PullRefreshState,
    isRefreshing: Boolean
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(2.dp)
            .pullRefresh(pullRefreshState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        item {
            Text(
                text = stringResource(id = R.string.network_error_message),
                style = MaterialTheme.typography.caption,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.primary,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(2.dp)
            )
        }

    }
    PullToRefreshIndicator(isRefreshing = isRefreshing, pullRefreshState = pullRefreshState)

}


@Composable
fun LoadingView() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        CircularProgressIndicator(modifier = Modifier.size(48.dp))
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PostListOfflineView(
    state: ViewModelState.Offline,
    navController: NavController,
    pullRefreshState: PullRefreshState,
    isRefreshing: Boolean
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(2.dp)
            .pullRefresh(pullRefreshState)
    ) {
        Text(
            text = "Offline",
            textAlign = TextAlign.Center,
            fontSize = MaterialTheme.typography.h6.fontSize,
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onPrimary,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.primary)
        )
        PostList(post = state.data, navController)
    }
    PullToRefreshIndicator(isRefreshing = isRefreshing, pullRefreshState = pullRefreshState)

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PostListOnlineView(
    isRefreshing: Boolean,
    isLoading: Boolean,
    pullRefreshState: PullRefreshState,
    state: ViewModelState.Success,
    listState: LazyListState,
    viewModel: RedditViewModel,
    navController: NavController
) {
    AnimatedVisibility(visible = isRefreshing.not(), enter = fadeIn(), exit = fadeOut()) {
        LazyColumn(
            state = listState,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(2.dp)
                .pullRefresh(pullRefreshState)
        ) {
            itemsIndexed(state.data) { index, item ->
                PostItem(post = item, index, navController)
            }
            item {
                // Loading state at end of list
                if (isLoading) {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .height(60.dp), contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }

    }
    PullToRefreshIndicator(isRefreshing = isRefreshing, pullRefreshState = pullRefreshState)

    LaunchedEffect(listState) {
        listState.interactionSource.interactions.collectLatest {
            val visibleItemInfo = listState.layoutInfo.visibleItemsInfo
            listState.layoutInfo.totalItemsCount
            if (visibleItemInfo.isNotEmpty() && visibleItemInfo.last().index >= listState.layoutInfo.totalItemsCount - 1) {
                if (isLoading.not()) {
                    viewModel.morePosts()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PullToRefreshIndicator(isRefreshing: Boolean, pullRefreshState: PullRefreshState) {
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

@Composable
fun PostItem(post: Post, index: Int, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 2.dp)
            .clickable {
                navController.navigate("detail/${post.id}")
            },
        elevation = 8.dp,
        backgroundColor = MaterialTheme.colors.primaryVariant,
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            PostImage(post = post)
            PostDetails(post = post, index)
        }
    }

}

@Composable
fun PostList(post: List<Post>, navController: NavController) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        itemsIndexed(post) { index, item ->
            PostItem(post = item, index, navController)
        }
    }
}

@Composable
fun PostDetails(post: Post, index: Int) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp)
    )
    {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = post.author, style = MaterialTheme.typography.subtitle1,
                fontSize = MaterialTheme.typography.caption.fontSize,
                color = MaterialTheme.colors.secondary
            )
            Text(
                text = post.timeStamp, style = MaterialTheme.typography.subtitle1,
                fontSize = MaterialTheme.typography.caption.fontSize,
                color = MaterialTheme.colors.secondary
            )
            Card(
                modifier = Modifier
                    .clip(CircleShape),
                backgroundColor = MaterialTheme.colors.onSecondary
            ) {
                Text(
                    modifier = Modifier.padding(4.dp),
                    text = post.flairText, style = MaterialTheme.typography.subtitle1,
                    fontSize = MaterialTheme.typography.caption.fontSize,
                    color = MaterialTheme.colors.primary
                )
            }
            Text(
                text = index.toString(), style = MaterialTheme.typography.subtitle1,
                fontSize = MaterialTheme.typography.caption.fontSize,
                color = MaterialTheme.colors.primary
            )
        }

        Text(
            text = post.title,
            style = MaterialTheme.typography.subtitle1,
            fontSize = MaterialTheme.typography.subtitle2.fontSize,
            color = MaterialTheme.colors.primary
        )
    }

}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PostImage(post: Post) {
    GlideImage(
        model = post.imageUrl,
        contentScale = ContentScale.Crop,
        contentDescription = "Post Image",
        modifier = Modifier
            .size(80.dp)
            .clip(RoundedCornerShape(20))
    )
}
