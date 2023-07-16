package com.lightscout.redditviewer.viewmodel

import com.lightscout.redditviewer.model.repository.RedditRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RedditViewModel @Inject constructor(redditRepository: RedditRepository): BaseViewModel<ViewModelState>(ViewModelState.Loading) {
    init {
        disposables += redditRepository.getPosts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                state.postValue(ViewModelState.Success(it))
            }, {
                state.postValue(ViewModelState.Error(it.message ?: "Unknown error"))
            })
    }

}