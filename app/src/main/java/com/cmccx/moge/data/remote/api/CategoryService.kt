package com.cmccx.moge.data.remote.api

import android.util.Log
import com.cmccx.moge.base.ApplicationClass
import com.cmccx.moge.base.BaseResponse
import com.cmccx.moge.data.remote.model.Category
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryService(val categoryView: CategoryView) {
    fun getCategory(jwt : String, userIdx: Int, category: Category){
        val categoryService = ApplicationClass.retrofitJ.create(CategoryRetrofitInterface::class.java)

        categoryService.sendCategory(jwt, userIdx, category).enqueue(object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    1005-> categoryView.onGetCategoryResultSuccess()
                    else-> categoryView.onGetCategoryResultFailure(resp.message)
                }
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                Log.d("send category", t.message ?: "통신 오류")
            }

        })
    }
}

class ModCategoryService(val modCategoryView: ModCategoryView) {
    fun modCategory(jwt : String, userIdx: Int, category: Category) {
        val modCategoryService = ApplicationClass.retrofitJ.create(CategoryRetrofitInterface::class.java)

        modCategoryService.modCategory(jwt, userIdx, category).enqueue(object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                val resp = response.body()!!
                when(resp.code) {
                    1006 -> modCategoryView.onModCategoryResultSuccess()
                    else -> modCategoryView.onModCategoryResultFailure(resp.message)
                }
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                Log.d("modify category", t.message ?: "통신 오류")
            }
        })
    }
}
