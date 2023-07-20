package com.lightscout.redditviewer

import androidx.lifecycle.SavedStateHandle
import com.lightscout.redditviewer.model.data.Post
import com.lightscout.redditviewer.model.repository.RedditRepository
import com.lightscout.redditviewer.util.TinyDB
import com.lightscout.redditviewer.viewmodel.RedditViewModel
import com.lightscout.redditviewer.viewmodel.ViewModelState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantExecutorExtension::class)
@ExtendWith(CoroutinesTestExtension::class)
@OptIn(ExperimentalCoroutinesApi::class)
class RedditViewModelTest {

    // Mock the RedditRepository

    private val redditRepository = mockk<RedditRepository>()

    // Mock the TinyDB
    private val tinyDb = mockk<TinyDB>()

    // Create the SavedStateHandle
    private val savedStateHandle = SavedStateHandle()

    // Create CoroutineDispatcher for testing
    private val testDispatcher = TestCoroutineDispatcher()

    // Create ViewModel with mocked RedditRepository, TinyDB and testDispatcher
    private val viewModel =
        RedditViewModel(redditRepository, tinyDb, savedStateHandle, testDispatcher)


    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `getPosts returns Success state when repository getPosts is successful`() = runBlocking {
        // Prepare data
        val posts = listOf<Post>()
        val repositoryResult = RedditRepository.RepositoryResult.Success(posts, "after")

        // Prepare mocks
        coEvery { redditRepository.getPosts() } returns repositoryResult
        coEvery { tinyDb.putListObject("posts", ArrayList(posts)) } returns Unit

        // Perform action
        viewModel.getPosts()

        // Wait for coroutine
        testDispatcher.scheduler.advanceUntilIdle()

        // Verify state
        val expectedState = ViewModelState.Success(posts)
        Assertions.assertEquals(expectedState, viewModel.state.value)
    }

    @Test
    fun `getPosts returns Error state when repository getPosts is unsuccessful`() = runBlocking {
        // Prepare data
        val repositoryResult = RedditRepository.RepositoryResult.Error("exception")

        // Prepare mocks
        coEvery { redditRepository.getPosts() } returns repositoryResult

        // Perform action
        viewModel.getPosts()

        // Wait for coroutine
        testDispatcher.scheduler.advanceUntilIdle()

        // Verify state
        val expectedState = ViewModelState.Error("exception")
        Assertions.assertEquals(expectedState, viewModel.state.value)
    }
}