package com.lightscout.redditviewer.model.repository

import com.lightscout.redditviewer.model.data.Post
import com.lightscout.redditviewer.model.service.RedditService
import com.lightscout.redditviewer.util.PostMapper
import io.reactivex.Single
import javax.inject.Inject

class RedditRepository @Inject constructor(
    private val redditService: RedditService,
    private val postMapper: PostMapper
) {
    fun getPosts(after: String? = null): Single<List<Post>> = redditService.getPosts(after).map {
        postMapper.map(it)
    }
}