package com.lightscout.redditviewer.model.repository

import com.lightscout.redditviewer.model.service.RedditService
import javax.inject.Inject

class RedditRepository @Inject constructor(
    private val redditService: RedditService
)   {
    fun getPosts(after: String? = null) = redditService.getPosts(after)
}