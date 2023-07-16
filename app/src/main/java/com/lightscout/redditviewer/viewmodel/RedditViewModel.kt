package com.lightscout.redditviewer.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.lightscout.redditviewer.model.repository.RedditRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class RedditViewModel (private val redditRepository: RedditRepository, savedStateHandle: SavedStateHandle) :
    BaseViewModel<ViewModelState>(ViewModelState.Loading, savedStateHandle) {
    @Inject constructor(
        redditRepository: RedditRepository
    ) : this(redditRepository, SavedStateHandle())
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

}