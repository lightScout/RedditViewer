package com.lightscout.redditviewer.model.service

import com.lightscout.redditviewer.model.data.RedditResponse
import com.lightscout.redditviewer.util.Constants.Companion.AFTER
import com.lightscout.redditviewer.util.Constants.Companion.LIMIT
import com.lightscout.redditviewer.util.Constants.Companion.LIMIT_VALUE
import com.lightscout.redditviewer.util.Constants.Companion.PATH
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RedditService {
    @GET(PATH)
    fun getPosts(@Query(AFTER) after : String? = null, @Query(LIMIT) limit : String = LIMIT_VALUE): Single<RedditResponse>
}