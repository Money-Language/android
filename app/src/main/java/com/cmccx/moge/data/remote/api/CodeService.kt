package com.cmccx.moge.data.remote.api

import com.cmccx.moge.base.ApplicationClass
import com.cmccx.moge.data.remote.model.Code
import com.cmccx.moge.data.remote.model.CodeResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CodeService(val codeView: CodeView) {
    fun getCode(code: Code){
        val codeService = ApplicationClass.retrofitJ.create(CodeRetrofitInterface::class.java)

        codeService.sendCode(code).enqueue(object : Callback<CodeResponse> {
            override fun onResponse(call: Call<CodeResponse>, response: Response<CodeResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    1000-> resp.result.let { codeView.onGetCodeResultSuccess(resp.result) }
                    else-> resp.message.let { codeView.onGetCodeResultFailure(it) }
                }
            }

            override fun onFailure(call: Call<CodeResponse>, t: Throwable) {
                codeView.onGetCodeResultFailure(t.message ?: "통신 오류")
            }

        })
    }
}
