package com.lightscout.redditviewer.viewmodel

sealed class ViewModelState {
    object Loading : ViewModelState()
    data class Success<T>(val data: T) : ViewModelState()
    data class Error(val message: String) : ViewModelState()
}