package com.lightscout.redditviewer.viewmodel

import androidx.annotation.MainThread
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel<S : Any>(initialState: ViewModelState, private val savedStateHandle: SavedStateHandle): ViewModel() {

    val state: StateFlow<ViewModelState> =
        savedStateHandle.getStateFlow("state", initialState)

    @MainThread
    protected fun setState(reducer: ViewModelState.() -> ViewModelState) {
        savedStateHandle["state"] = reducer(state.value)
    }

}