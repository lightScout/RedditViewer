package com.lightscout.redditviewer.model.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Post(val id : String, val title: String, val imageUrl: String, val author: String, val timeStamp: String, val rawTimeStamp: Int,  val flairText: String, val ups : String, val downs : String, val numComments : String) : Parcelable