package com.lightscout.redditviewer.model.repository

import com.lightscout.redditviewer.model.data.Post

interface RedditRepository {
    suspend fun getPosts(after: String? = null): RepositoryResult

    sealed class RepositoryResult {
        data class Success(val posts: List<Post>) : RepositoryResult()
        data class Error(val exception: String) : RepositoryResult()

    }
}