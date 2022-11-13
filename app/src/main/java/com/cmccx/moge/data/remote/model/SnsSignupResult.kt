package com.cmccx.moge.data.remote.model

import com.google.gson.annotations.SerializedName

data class SnsSignupResult(
    @SerializedName("email") val email: String,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("profileImage") val profileImage: String?,
    @SerializedName("socialCreatedID") val socialCreatedID: String,
    @SerializedName("status") val status: String,
)
