package com.lightscout.redditviewer.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.lightscout.redditviewer.model.data.Post
import com.lightscout.redditviewer.model.repository.RedditRepository
import com.lightscout.redditviewer.util.TinyDB
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RedditViewModel   (
    private val redditRepository: RedditRepository,
    private val tinyDb: TinyDB,
    savedStateHandle: SavedStateHandle,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) :
    BaseViewModel<ViewModelState>(ViewModelState.Loading, savedStateHandle) {
    @Inject
    constructor(
        redditRepository: RedditRepository,
        tinyDB: TinyDB
    ) : this(redditRepository, tinyDB, SavedStateHandle())

    private val _isRefreshing = MutableStateFlow(false)
    private val _isLoading = MutableStateFlow(false)
    private val recentPosts = ArrayList<Post>()
    private var after = ""

    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    val isLoading: StateFlow<Boolean>
        get() = _isLoading.asStateFlow()

    fun getPosts() {
        viewModelScope.launch(dispatcher) {
            redditRepository.getPosts().let { result ->
                setState {
                    when (result) {
                        is RedditRepository.RepositoryResult.Success -> {
                            after = result.after
                            tinyDb.putListObject("posts", ArrayList(result.posts))
                            recentPosts.addAll(result.posts)
                            ViewModelState.Success(result.posts)

                        }

                        is RedditRepository.RepositoryResult.Error -> {
                            ViewModelState.Error(result.exception)
                        }
                    }
                }
            }

        }

    }

    fun morePosts() {
        if (_isLoading.value) return
        viewModelScope.launch(dispatcher) {
            _isLoading.emit(true)
            // Simulated network delay
            delay(3000)
            redditRepository.getPosts(after).let { result ->
                setState {
                    when (result) {
                        is RedditRepository.RepositoryResult.Success -> {
                            after = result.after
                            val posts = ArrayList<Post>()
                            posts.addAll(recentPosts)
                            recentPosts.addAll(result.posts)
                            posts.addAll(result.posts)
                            tinyDb.putListObject("posts", ArrayList(result.posts))
                            viewModelScope.launch(dispatcher) {
                                _isLoading.emit(false)
                            }
                            ViewModelState.Success(posts)
                        }

                        is RedditRepository.RepositoryResult.Error -> {
                            ViewModelState.Error(result.exception)
                        }
                    }
                }
            }
        }
    }

    fun getPost(postId: String): Post? {
        return recentPosts.find { it.id == postId }
    }

    fun fromCache() {
        viewModelScope.launch(dispatcher) {
            _isRefreshing.emit(true)
            tinyDb.getListObject("posts", Post::class.java)?.let { posts ->
                setState {
                    viewModelScope.launch(dispatcher) {
                        _isRefreshing.emit(false)
                    }
                    recentPosts.addAll(posts.filterIsInstance<Post>())
                    ViewModelState.Offline(posts.filterIsInstance<Post>())
                }
            }
        }
    }

    fun refresh() {
        viewModelScope.launch(dispatcher) {
            _isRefreshing.emit(true)
            redditRepository.getPosts().let { result ->
                setState {
                    when (result) {
                        is RedditRepository.RepositoryResult.Success -> {
                            viewModelScope.launch(dispatcher) {
                                _isRefreshing.emit(false)
                            }
                            tinyDb.putListObject("posts", ArrayList(result.posts))
                            ViewModelState.Success(result.posts)
                        }

                        is RedditRepository.RepositoryResult.Error -> {
                            viewModelScope.launch(dispatcher) {
                                _isRefreshing.emit(false)
                            }
                            ViewModelState.Error(result.exception)
                        }
                    }
                }
            }

        }
    }
}

