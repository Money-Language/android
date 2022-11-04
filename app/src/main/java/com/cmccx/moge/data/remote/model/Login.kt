package com.cmccx.moge.data.remote.model

import com.google.gson.annotations.SerializedName

// 일반(이메일) 로그인
data class Login(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
)

// 소셜 로그인 (카카오, 네이버)
data class SnsLogin(
    @SerializedName("accessToken") val accessToken: String,
    @SerializedName("nickname") val nickname: String,
)
