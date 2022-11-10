package com.cmccx.moge.data.remote.model

import com.google.gson.annotations.SerializedName

// 이메일 인증 코드
data class Code(
    @SerializedName("email") val email: String,
    @SerializedName("code") val code: String,
)
