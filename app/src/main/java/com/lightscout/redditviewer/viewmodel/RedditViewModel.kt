package com.lightscout.redditviewer.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.lightscout.redditviewer.model.repository.RedditRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class RedditViewModel (private val redditRepository: RedditRepository, savedStateHandle: SavedStateHandle) :
    BaseViewModel<ViewModelState>(ViewModelState.Loading, savedStateHandle) {
    @Inject constructor(
        redditRepository: RedditRepository
    ) : this(redditRepository, SavedStateHandle())

    private val _isRefreshing = MutableStateFlow(false)

    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    init {
        viewModelScope.launch {
            redditRepository.getPosts().let { result ->
                setState {
                    when (result) {
                        is RedditRepository.RepositoryResult.Success -> {
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