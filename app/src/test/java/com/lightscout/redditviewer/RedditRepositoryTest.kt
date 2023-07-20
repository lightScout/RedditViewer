package com.lightscout.redditviewer

import com.lightscout.redditviewer.model.data.Data
import com.lightscout.redditviewer.model.data.RedditResponse
import com.lightscout.redditviewer.model.repository.RedditRepository
import com.lightscout.redditviewer.model.repository.RedditRepositoryImpl
import com.lightscout.redditviewer.model.service.RedditService
import com.lightscout.redditviewer.util.PostMapperImpl
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

class RedditRepositoryTest {

    @Mock
    private lateinit var redditService: RedditService

    private lateinit var repository: RedditRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repository = RedditRepositoryImpl(redditService, PostMapperImpl())
    }

    @Test
    fun `getPosts returns success when API call is successful`() = runBlocking {
        // Given
        whenever(redditService.getPosts(after)).thenReturn(redditResponse)
        // When
        val result = repository.getPosts(after)
        // Then
        assertTrue(result is RedditRepository.RepositoryResult.Success)
        assertEquals(mappedResponse, result)
    }

    @Test
    fun `getPosts returns error when API call fails`() = runBlocking {
        // Given
        whenever(redditService.getPosts(null)).thenThrow(RuntimeException(errorMessage))

        // When
        val result = repository.getPosts(null)

        // Then
        assertTrue(result is RedditRepository.RepositoryResult.Error)
        assertEquals(errorMessage, (result as RedditRepository.RepositoryResult.Error).exception)
    }

    companion object {
        private val data = Data(
            after = "after",
            before = "before",
            children = listOf(),
            dist = 0,
            modhash = "",
            geo_filter = ""
        )
        private val redditResponse = RedditResponse(data = data, kind = "")
        val mappedResponse = RedditRepository.RepositoryResult.Success(listOf(), "after")
        private const val after = "after"
        private const val errorMessage = "Unknown error"
    }
}
