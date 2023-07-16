package com.lightscout.redditviewer.viewmodel

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel<S : Any>(initialState: S): ViewModel() {
    protected val disposables: CompositeDisposable = CompositeDisposable()

    private val _state: MutableLiveData<S> = MutableLiveData(initialState)

    val state: LiveData<S> get() = _state

    @MainThread
    protected fun setState(reducer: S.() -> S) {
        val currentState = _state.value!!
        val newState = currentState.reducer()
        if (newState != currentState) {
            _state.value = newState
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}