package com.cmccx.moge.data.remote.model

import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("categoryIdx") val categoryIdx: List<String>,
)
