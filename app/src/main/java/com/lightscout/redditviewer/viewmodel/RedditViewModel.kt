package com.lightscout.redditviewer.viewmodel

import com.lightscout.redditviewer.model.repository.RedditRepository
import com.lightscout.redditviewer.util.plusAssign
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RedditViewModel @Inject constructor(private val redditRepository: RedditRepository) :
    BaseViewModel<ViewModelState>(ViewModelState.Loading) {
    init {
        setState {
            ViewModelState.Loading
        }
        disposables += redditRepository.getPosts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ posts ->
                setState { ViewModelState.Success(posts) }
            }, {
//                state.postValue(ViewModelState.Error(it.message ?: "Unknown error"))
            })
    }

}