package com.cmccx.moge.data.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Board(
    @SerializedName("boardIdx") val boardIdx : Int,
    @SerializedName("nickname") val nickname : String,
    @SerializedName("profileImage") val profileImage : String?,
    @SerializedName("elapsedTime") val elapsedTime : String,
    @SerializedName("title") val title : String,
    @SerializedName("quizCount") val quizCount : Int,
    @SerializedName("viewCount") val viewCount : Int,
    @SerializedName("likeCount") val likeCount : Int,
    @SerializedName("commentCount") val commentCount : Int,
) : Parcelable
