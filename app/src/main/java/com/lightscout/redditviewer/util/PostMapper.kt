package com.lightscout.redditviewer.util

import com.lightscout.redditviewer.model.data.Post
import com.lightscout.redditviewer.model.data.RedditResponse

open class PostMapper {

    fun map(redditResponse: RedditResponse): List<Post> {
        val post = ArrayList<Post>()

        for ((data, _) in redditResponse.data.children) {
            post.add(
                Post(
                    data.id,
                    data.title,
                    data.thumbnail,
                    data.author,
                    Common().secondsToDays(data.created.toInt()),
                    data.created.toInt(),
                    data.link_flair_text,
                    data.ups.toString(),
                    data.downs.toString(),
                    data.num_comments.toString()
                )
            )
        }
        return post
    }
}