package com.lightscout.redditviewer.model.api

import com.lightscout.redditviewer.model.data.RedditResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RedditApi {
    @GET("/r/technology/new.json")
    suspend fun getPosts(@Query("after") after : String? = null): RedditResponse
}