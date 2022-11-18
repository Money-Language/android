package com.cmccx.moge.data.remote.api

import android.util.Log
import com.cmccx.moge.base.ApplicationClass
import com.cmccx.moge.base.BaseResponse
import com.cmccx.moge.data.remote.model.ModPassword
import com.cmccx.moge.data.remote.model.ProfileResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileService(val profileView: ProfileView) {
    fun getProfile(jwt : String, userIdx: Int){
        val profileService = ApplicationClass.retrofitJ.create(ProfileRetrofitInterface::class.java)

        profileService.getProfile(jwt, userIdx).enqueue(object : Callback<ProfileResponse> {
            override fun onResponse(call: Call<ProfileResponse>, response: Response<ProfileResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    1000-> profileView.onGetProfileResultSuccess(resp.result)
                    else-> profileView.onGetProfileResultFailure(resp.message)
                }
            }

            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                Log.d("프로필 조회", t.message ?: "통신 오류")
            }
        })
    }
}

class ModProfileService(val modProfileView: ModProfileView) {
    fun modProfile(jwt : String, userIdx: Int, profileImg: MultipartBody.Part, nickname: String){
        val modProfileService = ApplicationClass.retrofitJ.create(ProfileRetrofitInterface::class.java)

        modProfileService.changeProfile(jwt, userIdx, profileImg, nickname).enqueue(object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    1009-> modProfileView.onModProfileResultSuccess()
                    else-> modProfileView.onModProfileResultFailure(resp.message)
                }
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                Log.d("프로필 수정", t.message ?: "통신 오류")
            }
        })
    }
}

class DelProfileService(val delProfileView: DelProfileView) {
    fun delProfile(jwt : String, userIdx: Int){
        val delProfileService = ApplicationClass.retrofitJ.create(ProfileRetrofitInterface::class.java)

        delProfileService.deleteProfileImg(jwt, userIdx).enqueue(object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    1008-> delProfileView.onDelProfileResultSuccess()
                    else-> delProfileView.onDelProfileResultFailure(resp.message)
                }
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                Log.d("프로필 이미지 삭제", t.message ?: "통신 오류")
            }
        })
    }
}

class ModPasswordService(val modPasswordView: ModPasswordView) {
    fun modPassword(jwt : String, userIdx: Int, modPassword: ModPassword){
        val modPasswordService = ApplicationClass.retrofitJ.create(ProfileRetrofitInterface::class.java)

        modPasswordService.changePassword(jwt, userIdx, modPassword).enqueue(object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    1002-> modPasswordView.onModPasswordResultSuccess()
                    else-> modPasswordView.onModPasswordResultFailure(resp.message)
                }
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                Log.d("비밀번호 수정", t.message ?: "통신 오류")
            }
        })
    }
}
