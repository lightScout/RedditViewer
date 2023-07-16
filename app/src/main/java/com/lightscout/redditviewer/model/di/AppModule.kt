package com.lightscout.redditviewer.model.di

import com.lightscout.redditviewer.model.repository.RedditRepository
import com.lightscout.redditviewer.model.service.RedditService
import com.lightscout.redditviewer.util.Constants.Companion.BASE_URL
import com.lightscout.redditviewer.util.PostMapper
import com.lightscout.redditviewer.viewmodel.RedditViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideRedditService(): RedditService {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
            .create(RedditService::class.java)
    }

    @Provides
    fun provideRedditRepository(
        redditService: RedditService,
        postMapper: PostMapper
    ): RedditRepository {
        return RedditRepository(redditService, postMapper)
    }

    @Provides
    fun providePostMapper(): PostMapper {
        return PostMapper()
    }

    @Provides
    fun provideViewModel(redditRepository: RedditRepository): RedditViewModel {
        return RedditViewModel(redditRepository = redditRepository)
    }

}