package com.cmccx.moge.data.remote.api

interface CategoryView {
    fun onGetCategoryResultSuccess()
    fun onGetCategoryResultFailure(message: String)
}