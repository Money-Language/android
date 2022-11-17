package com.cmccx.moge.data.remote.model

data class Following (
    val userIdx: String,
    val nickname: String,
    val profileImage: String
)

data class FollowingPage (
    val page: Int
)