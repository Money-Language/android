package com.cmccx.moge.data.remote.api

import android.util.Log
import com.cmccx.moge.base.ApplicationClass
import com.cmccx.moge.base.BaseResponse
import com.cmccx.moge.data.remote.model.BoardResponse
import com.cmccx.moge.data.remote.model.TopTenResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchService(val searchView: SearchView) {
    fun getSearchResult(){
        val searchService = ApplicationClass.retrofitJ.create(SearchRetrofitInterface::class.java)

        searchService.getSearch().enqueue(object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    1000-> searchView.onGetSearchResultSuccess()
                    else-> searchView.onGetSearchResultFailure(resp.message)
                }
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                Log.d("검색 API", t.message ?: "통신 오류")
            }
        })
    }
}

class TopViewService(val topView: TopView) {
    fun getTopViewResult(){
        val topTenService = ApplicationClass.retrofitJ.create(TopTenRetrofitInterface::class.java)

        topTenService.getTopView().enqueue(object : Callback<TopTenResponse> {
            override fun onResponse(call: Call<TopTenResponse>, response: Response<TopTenResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    1000-> topView.onGetTopViewResultSuccess(resp.result)
                    else-> topView.onGetTopViewResultFailure(resp.message)
                }
            }

            override fun onFailure(call: Call<TopTenResponse>, t: Throwable) {
                Log.d("탑텐 API", t.message ?: "통신 오류")
            }
        })
    }
}

class TopLikeService(val topLike: TopLike) {
    fun getTopLikeResult(){
        val topTenService = ApplicationClass.retrofitJ.create(TopTenRetrofitInterface::class.java)

        topTenService.getTopLike().enqueue(object : Callback<TopTenResponse> {
            override fun onResponse(call: Call<TopTenResponse>, response: Response<TopTenResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    1000-> topLike.onGetTopLikeResultSuccess(resp.result)
                    else-> topLike.onGetTopLikeResultFailure(resp.message)
                }
            }

            override fun onFailure(call: Call<TopTenResponse>, t: Throwable) {
                Log.d("탑텐 API", t.message ?: "통신 오류")
            }
        })
    }
}

class QuizLatestService(val quizOrderView: QuizOrderView) {
    fun getQuizLatestResult(jwt: String, categoryIdx: Int){
        val quizLatestService = ApplicationClass.sWannyRetrofit.create(QuizTenRetrofitInterface::class.java)

        quizLatestService.getQuizLatest(jwt, categoryIdx).enqueue(object : Callback<BoardResponse> {
            override fun onResponse(call: Call<BoardResponse>, response: Response<BoardResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    1000-> quizOrderView.onGetQuizOrderResultSuccess(resp.result)
                    else-> quizOrderView.onGetQuizOrderResultFailure(resp.message)
                }
            }

            override fun onFailure(call: Call<BoardResponse>, t: Throwable) {
                Log.d("퀴즈 최신순 API", t.message ?: "통신 오류")
            }
        })
    }
}

class QuizOrderService(val quizOrderView: QuizOrderView) {
    fun getQuizViewOrderResult(jwt: String, categoryIdx: Int, order: String) {
        val quizViewOrderService = ApplicationClass.sWannyRetrofit.create(QuizTenRetrofitInterface::class.java)

        quizViewOrderService.getQuizOrder(jwt, categoryIdx, order).enqueue(object : Callback<BoardResponse> {
            override fun onResponse(call: Call<BoardResponse>, response: Response<BoardResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    1000-> quizOrderView.onGetQuizOrderResultSuccess(resp.result)
                    else-> quizOrderView.onGetQuizOrderResultFailure(resp.message)
                }
            }

            override fun onFailure(call: Call<BoardResponse>, t: Throwable) {
                Log.d("퀴즈 최신순 API", t.message ?: "통신 오류")
            }
        })
    }

    fun getQuizLikeOrderResult(jwt: String, categoryIdx: Int, order: String) {
        val quizLikeOrderService = ApplicationClass.sWannyRetrofit.create(QuizTenRetrofitInterface::class.java)

        quizLikeOrderService.getQuizOrder(jwt, categoryIdx, order).enqueue(object : Callback<BoardResponse> {
            override fun onResponse(call: Call<BoardResponse>, response: Response<BoardResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    1000-> quizOrderView.onGetQuizOrderResultSuccess(resp.result)
                    else-> quizOrderView.onGetQuizOrderResultFailure(resp.message)
                }
            }

            override fun onFailure(call: Call<BoardResponse>, t: Throwable) {
                Log.d("퀴즈 최신순 API", t.message ?: "통신 오류")
            }
        })
    }
}