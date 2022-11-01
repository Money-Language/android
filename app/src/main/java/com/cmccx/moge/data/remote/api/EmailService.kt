package com.cmccx.moge.data.remote.api

import android.util.Log
import com.cmccx.moge.base.ApplicationClass
import com.cmccx.moge.base.BaseResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmailService(val emailView: EmailView) {
    fun getEmail(email: String){
        val emailService = ApplicationClass.retrofitJ.create(EmailRetrofitInterface::class.java)

        emailService.sendEmail(email).enqueue(object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    1000-> emailView.onGetEmailResultSuccess()
                    else-> emailView.onGetEmailResultFailure(resp.message)
                }

                /*when(resp.code){
                    1000-> Log.d("send email", resp.message)
                    else-> Log.d("send email", resp.message)
                }*/
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                Log.d("send email", t.message ?: "통신 오류")
            }

        })
    }
}
