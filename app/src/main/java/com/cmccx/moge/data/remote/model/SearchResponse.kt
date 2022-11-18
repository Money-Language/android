package com.cmccx.moge.data.remote.model

import com.cmccx.moge.base.BaseResponse
import com.google.gson.annotations.SerializedName

data class KeywordResponse(
    @SerializedName("result") val result: ArrayList<Keyword>
) : BaseResponse()

data class SearchResponse(
    @SerializedName("result") val result: ArrayList<Search>?
) : BaseResponse()
