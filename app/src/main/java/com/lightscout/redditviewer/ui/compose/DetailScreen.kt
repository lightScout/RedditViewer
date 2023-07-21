package com.lightscout.redditviewer.ui.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.lightscout.redditviewer.R
import com.lightscout.redditviewer.util.Common
import com.lightscout.redditviewer.viewmodel.RedditViewModel

@Composable
fun DetailScreen(postId: String, viewModel: RedditViewModel) {
    val post = viewModel.getPost(postId)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxSize()
    ) {

        Card(shape = MaterialTheme.shapes.medium, modifier = Modifier.fillMaxWidth()) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = "Timestamp:",
                    color = MaterialTheme.colors.primary,
                    style = MaterialTheme.typography.h6
                )
                Text(text = post?.rawTimeStamp?.let { Common().calculateTime(it) } ?: "",
                    color = MaterialTheme.colors.primary,
                    style = MaterialTheme.typography.body2)
            }

        }

        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_thumb_up_24),
                    contentDescription = "Up votes icon"
                )
                Text(text = post?.ups ?: "", color = MaterialTheme.colors.primary)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_thumb_down_24),
                    contentDescription = "Down votes icon"
                )
                Text(text = post?.downs ?: "", color = MaterialTheme.colors.primary)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_comment_24),
                    contentDescription = "Comments icon"
                )
                Text(text = post?.numComments ?: "", color = MaterialTheme.colors.primary)
            }
        }

    }


}
