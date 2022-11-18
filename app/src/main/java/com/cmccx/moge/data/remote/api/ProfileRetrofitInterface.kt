package com.cmccx.moge.data.remote.api

import com.cmccx.moge.base.BaseResponse
import com.cmccx.moge.data.remote.model.ModPassword
import com.cmccx.moge.data.remote.model.ProfileResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ProfileRetrofitInterface {
    // 프로필 정보 조회
    @GET("/app/users/{userIdx}/profile")
    fun getProfile(
        @Header("X-ACCESS-TOKEN") jwt : String, // jwt
        @Path("userIdx") userIdx : Int,
    ): Call<ProfileResponse>

    // 프로필 변경
    @Multipart
    @PATCH("/app/users/{userIdx}/profile")
    fun changeProfile(
        @Header("X-ACCESS-TOKEN") jwt: String,
        @Path("userIdx") userIdx : Int,
        @Part profileImage: MultipartBody.Part?,
        @Part ("nickname") nickname: String,
    ): Call<BaseResponse>

    // 프로필 이미지 삭제
    @DELETE("/app/users/{userIdx}/profile")
    fun deleteProfileImg(
        @Header("X-ACCESS-TOKEN") jwt: String,
        @Path("userIdx") userIdx : Int,
    ): Call<BaseResponse>

    // 비밀번호 변경
    @PATCH("/app/users/{userIdx}/password")
    fun changePassword(
        @Header("X-ACCESS-TOKEN") jwt: String,
        @Path("userIdx") userIdx : Int,
        @Body modPassword: ModPassword,
    ): Call<BaseResponse>
}