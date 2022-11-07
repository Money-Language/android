package com.cmccx.moge.data.remote.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class QuizBoard(
    val boardIdx: Int,
    val nickname: String,
    val profileImage: String?,
    val elapsedTime : String,
    val title: String,
    val quizCount: String,
    val viewCount : String,
    val likeCount: String
) : Parcelable
