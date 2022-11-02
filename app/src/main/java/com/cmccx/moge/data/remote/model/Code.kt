package com.cmccx.moge.data.remote.model

import com.google.gson.annotations.SerializedName

data class Code(
    @SerializedName("email") val email: String,
    @SerializedName("code") val code: String,
)
