package com.cmccx.moge.data.remote.model

import com.google.gson.annotations.SerializedName

data class EmailValid(
    @SerializedName("email") val email: String,
    @SerializedName("code") val code: String,
)
