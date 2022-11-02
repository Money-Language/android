package com.cmccx.moge.data.remote.model

import com.google.gson.annotations.SerializedName

data class UserResult(
    @SerializedName("jwt") val jwt: String,
    @SerializedName("userIdx") val userIdx: Int,
)
