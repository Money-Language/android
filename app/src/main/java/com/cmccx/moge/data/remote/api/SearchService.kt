package com.cmccx.moge.data.remote.api

import android.util.Log
import com.cmccx.moge.base.ApplicationClass
import com.cmccx.moge.base.BaseResponse
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

class TopViewService(val topTenView: TopViewView) {
    fun getTopViewResult(){
        val topTenService = ApplicationClass.retrofitJ.create(TopTenRetrofitInterface::class.java)

        topTenService.getTopView().enqueue(object : Callback<TopTenResponse> {
            override fun onResponse(call: Call<TopTenResponse>, response: Response<TopTenResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    1000-> topTenView.onGetTopViewResultSuccess(resp.result)
                    else-> topTenView.onGetTopViewResultFailure(resp.message)
                }
            }

            override fun onFailure(call: Call<TopTenResponse>, t: Throwable) {
                Log.d("탑텐 API", t.message ?: "통신 오류")
            }

        })
    }
}

class TopLikeService(val topLikeView: TopLikeView) {
    fun getTopLikeResult(){
        val topTenService = ApplicationClass.retrofitJ.create(TopTenRetrofitInterface::class.java)

        topTenService.getTopLike().enqueue(object : Callback<TopTenResponse> {
            override fun onResponse(call: Call<TopTenResponse>, response: Response<TopTenResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    1000-> topLikeView.onGetTopLikeResultSuccess(resp.result)
                    else-> topLikeView.onGetTopLikeResultFailure(resp.message)
                }
            }

            override fun onFailure(call: Call<TopTenResponse>, t: Throwable) {
                Log.d("탑텐 API", t.message ?: "통신 오류")
            }

        })
    }
}