package com.cmccx.moge.data.remote.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("jwt") val jwt: String,
    @SerializedName("userIdx") val userIdx: Int,
)

data class Profile(
    @SerializedName("userIdx") val userIdx: Int,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("profileImage") val profileImage: String?,
    @SerializedName("userPoint") val userPoint: Int,
    @SerializedName("followerCount") val followerCount: Int,
    @SerializedName("followingCount") val followingCount: Int,
)
