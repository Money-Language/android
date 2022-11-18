package com.cmccx.moge.data.remote.model

import com.google.gson.annotations.SerializedName

// 이메일 인증 코드
data class Password(
    @SerializedName("password") val password: String,
    @SerializedName("rePassword") val rePassword: String,
)

data class ModPassword(
    @SerializedName("currentPassword") val currentPassword: String,
    @SerializedName("modPassword") val modPassword: String,
    @SerializedName("reModPassword") val reModPassword: String,
)
