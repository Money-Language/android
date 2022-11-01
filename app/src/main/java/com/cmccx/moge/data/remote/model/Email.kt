package com.cmccx.moge.data.remote.model

import com.google.gson.annotations.SerializedName

data class Email(
    @SerializedName("email") val email: String,
)
