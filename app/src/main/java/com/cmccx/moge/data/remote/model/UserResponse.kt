package com.cmccx.moge.data.remote.model

import com.cmccx.moge.base.BaseResponse
import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("result") val result: User
) : BaseResponse()


// 프로필 정보 조회
data class ProfileResponse(
    @SerializedName("result") val result: Profile
) : BaseResponse()
