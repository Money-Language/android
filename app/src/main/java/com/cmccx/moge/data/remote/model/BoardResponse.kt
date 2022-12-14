package com.cmccx.moge.data.remote.model

import com.cmccx.moge.base.BaseResponse
import com.google.gson.annotations.SerializedName

data class BoardResponse(
    @SerializedName("result") val result: ArrayList<Board>
) : BaseResponse()

data class MyQuizResponse(
    @SerializedName("result") val result: ArrayList<MyQuiz>?
) : BaseResponse()

data class MyFavResponse(
    @SerializedName("result") val result: ArrayList<MyFav>?
) : BaseResponse()