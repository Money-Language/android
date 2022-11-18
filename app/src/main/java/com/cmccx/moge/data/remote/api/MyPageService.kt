package com.cmccx.moge.data.remote.api

import android.util.Log
import com.cmccx.moge.base.ApplicationClass
import com.cmccx.moge.data.remote.model.MyFavResponse
import com.cmccx.moge.data.remote.model.MyQuizResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyQuizAllService(val myQuizAllView: MyQuizAllView) {
    fun getMyQuizAll(jwt : String, userIdx: Int){
        val myQuizAllService = ApplicationClass.sWannyRetrofit.create(MyPageRetrofitInterface::class.java)

        myQuizAllService.getMyQuizAll(jwt, userIdx).enqueue(object : Callback<MyQuizResponse> {
            override fun onResponse(call: Call<MyQuizResponse>, response: Response<MyQuizResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    1000-> myQuizAllView.onGetMyQuizAllResultSuccess(resp.result)
                    else-> myQuizAllView.onGetMyQuizAllResultFailure(resp.code, resp.message)
                }
            }

            override fun onFailure(call: Call<MyQuizResponse>, t: Throwable) {
                Log.d("내 퀴즈 전체 조회", t.message ?: "통신 오류")
            }

        })
    }
}

class MyQuizCateService(val myQuizCateView: MyQuizCateView) {
    fun getMyQuizCate(jwt : String, userIdx: Int, category: String){
        val myQuizCateService = ApplicationClass.sWannyRetrofit.create(MyPageRetrofitInterface::class.java)

        myQuizCateService.getMyQuizCate(jwt, userIdx, category).enqueue(object : Callback<MyQuizResponse> {
            override fun onResponse(call: Call<MyQuizResponse>, response: Response<MyQuizResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    1000-> myQuizCateView.onGetMyQuizCateResultSuccess(resp.result)
                    else-> myQuizCateView.onGetMyQuizCateResultFailure(resp.message)
                }
            }

            override fun onFailure(call: Call<MyQuizResponse>, t: Throwable) {
                Log.d("카테고리 별 내 퀴즈 조회", t.message ?: "통신 오류")
            }

        })
    }
}

class MyFavService(val myFavView: MyFavView) {
    fun getMyFav(jwt : String, userIdx: Int) {
        val myFavService = ApplicationClass.retrofitJ.create(MyPageRetrofitInterface::class.java)

        myFavService.getMyFav(jwt, userIdx).enqueue(object : Callback<MyFavResponse> {
            override fun onResponse(call: Call<MyFavResponse>, response: Response<MyFavResponse>) {
                val resp = response.body()!!
                when(resp.code) {
                    1000 -> myFavView.onModMyFavResultSuccess()
                    else -> myFavView.onModMyFavResultFailure(resp.message)
                }
            }

            override fun onFailure(call: Call<MyFavResponse>, t: Throwable) {
                Log.d("내 좋아요 퀴즈 조회", t.message ?: "통신 오류")
            }
        })
    }
}
