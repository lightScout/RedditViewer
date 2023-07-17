package com.lightscout.redditviewer.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.lightscout.redditviewer.model.data.Post
import com.lightscout.redditviewer.model.repository.RedditRepository
import com.lightscout.redditviewer.util.TinyDB
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class RedditViewModel(
    private val redditRepository: RedditRepository,
    private val tinyDb: TinyDB,
    savedStateHandle: SavedStateHandle
) :
    BaseViewModel<ViewModelState>(ViewModelState.Loading, savedStateHandle) {
    @Inject
    constructor(
        redditRepository: RedditRepository,
        tinyDB: TinyDB
    ) : this(redditRepository, tinyDB, SavedStateHandle())

    private val _isRefreshing = MutableStateFlow(false)
    private val _isOffline = MutableStateFlow(false)

    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    val isOfflineState: StateFlow<Boolean>
        get() = _isOffline.asStateFlow()

    fun getPosts() {
        viewModelScope.launch {
            redditRepository.getPosts().let { result ->
                setState {
                    when (result) {
                        is RedditRepository.RepositoryResult.Success -> {
                            viewModelScope.launch {
                                _isOffline.emit(false)
                            }
                            tinyDb.putListObject("posts", ArrayList(result.posts))
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

    fun fromCache() {
        viewModelScope.launch {
            _isOffline.emit(true)
            tinyDb.getListObject("posts", Post::class.java)?.let { posts ->
                setState {
                    ViewModelState.Success(posts.filterIsInstance<Post>())
                }
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.emit(true)
            redditRepository.getPosts().let { result ->
                setState {
                    when (result) {
                        is RedditRepository.RepositoryResult.Success -> {
                            viewModelScope.launch {
                                _isRefreshing.emit(false)
                            }
                            ViewModelState.Success(result.posts)
                        }

                        is RedditRepository.RepositoryResult.Error -> {
                            viewModelScope.launch {
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

