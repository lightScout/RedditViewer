package com.lightscout.redditviewer.util

import com.lightscout.redditviewer.model.data.Post
import com.lightscout.redditviewer.model.data.RedditResponse

class PostMapper() {

    fun map(redditResponse: RedditResponse): List<Post> {
        val post = ArrayList<Post>()

        for ((data, _) in redditResponse.data.children) {
            post.add(Post(data.title))
        }
        return post
    }
}