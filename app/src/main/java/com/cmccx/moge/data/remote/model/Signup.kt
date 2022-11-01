package com.cmccx.moge.data.remote.model

import com.google.gson.annotations.SerializedName

data class Signup(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("rePassword") val rePassword: String,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("contract1") val contract1 : String,
    @SerializedName("contract2") val contract2: String,
    @SerializedName("contract3") val contract3: String,
    @SerializedName("contract4") val contract4: String?,
)
