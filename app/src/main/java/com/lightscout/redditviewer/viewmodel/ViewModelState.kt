package com.lightscout.redditviewer.viewmodel

import com.lightscout.redditviewer.model.data.Post
import java.io.Serializable

sealed class ViewModelState: Serializable {
    object Loading : ViewModelState()
    data class Success(val data: List<Post>) : ViewModelState()
    data class Error(val message: String) : ViewModelState()
}