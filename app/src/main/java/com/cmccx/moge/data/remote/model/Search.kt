package com.cmccx.moge.data.remote.model

import com.google.gson.annotations.SerializedName

data class TopTen(
        @SerializedName("categoryName") val categoryName: String,
        @SerializedName("title") val title: String,
        @SerializedName("viewCount") val viewCount: Int,
        @SerializedName("likeCount") val likeCount: Int,
        @SerializedName("quizCount") val quizCount: Int,
        @SerializedName("nickname") val nickname: Int,
        @SerializedName("profileImage") val profileImage: String,
)