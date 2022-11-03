package com.cmccx.moge.data.remote.model

import com.cmccx.moge.base.BaseResponse

data class HomeCateResponse(
    val result: List<QuizCategory>
) : BaseResponse()

data class HomeBoardResponse(
    val result: List<QuizBoard>
) : BaseResponse()

