package com.cmccx.moge.data.remote.model

import com.cmccx.moge.base.BaseResponse
import com.google.gson.annotations.SerializedName

data class SnsSignupResponse(
    @SerializedName("result") val result: SnsSignupResult?
) : BaseResponse()
