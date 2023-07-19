package com.lightscout.redditviewer.model.repository

import com.lightscout.redditviewer.model.data.RedditResponse
import com.lightscout.redditviewer.model.service.RedditService
import com.lightscout.redditviewer.util.PostMapper
import javax.inject.Inject

class RedditRepositoryImpl @Inject constructor(
    private val redditService: RedditService,
    private val postMapper: PostMapper
) : RedditRepository {
    override suspend fun getPosts(after: String?): RedditRepository.RepositoryResult {
        return try {
            val response = redditService.getPosts(after)
            response.toResult(postMapper)
        } catch (e: Exception) {
            RedditRepository.RepositoryResult.Error(e.localizedMessage ?: "Unknown error")
        }
    }
}

private fun RedditResponse.toResult(postMapper: PostMapper): RedditRepository.RepositoryResult {
    return RedditRepository.RepositoryResult.Success(postMapper.map(this), this.data.after)
}
