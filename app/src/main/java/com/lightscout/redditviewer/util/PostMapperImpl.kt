package com.lightscout.redditviewer.util

import com.lightscout.redditviewer.model.data.Post
import com.lightscout.redditviewer.model.data.RedditResponse

class PostMapperImpl : PostMapper {

    override fun map(redditResponse: RedditResponse): List<Post> {
        val posts = ArrayList<Post>()

        for ((data, _) in redditResponse.data.children) {
            posts.add(
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
        return posts
    }
}