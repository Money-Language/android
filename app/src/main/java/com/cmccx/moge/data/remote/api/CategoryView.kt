package com.cmccx.moge.data.remote.api

interface CategoryView {
    fun onGetCategoryResultSuccess()
    fun onGetCategoryResultFailure(message: String)
}

interface ModCategoryView {
    fun onModCategoryResultSuccess()
    fun onModCategoryResultFailure(message: String)
}