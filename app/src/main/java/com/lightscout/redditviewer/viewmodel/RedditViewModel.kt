package com.lightscout.redditviewer.viewmodel

import androidx.lifecycle.viewModelScope
import com.lightscout.redditviewer.model.repository.RedditRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class RedditViewModel @Inject constructor(private val redditRepository: RedditRepository) :
    BaseViewModel<ViewModelState>(ViewModelState.Loading) {
    init {
        setState {
            ViewModelState.Loading
        }
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