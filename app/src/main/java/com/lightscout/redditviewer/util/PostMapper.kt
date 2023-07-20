package com.lightscout.redditviewer.util

import com.lightscout.redditviewer.model.data.Post
import com.lightscout.redditviewer.model.data.RedditResponse

interface PostMapper {
    fun map(redditResponse: RedditResponse): List<Post>
}