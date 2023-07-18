package com.lightscout.redditviewer.model.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Post(val title: String, val imageUrl: String, val author: String, val timeStamp: String) : Parcelable