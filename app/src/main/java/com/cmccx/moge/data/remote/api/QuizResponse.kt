package com.cmccx.moge.data.remote.model

import com.cmccx.moge.base.BaseResponse

data class QuizQuestionResponse (
    val result: List<QuizQuestion>
) : BaseResponse()

data class QuizChoiceResponse (
    val result: List<QuizChoice>
) : BaseResponse()

data class QuizAnswerResponse (
    val result: List<QuizAnswer>
) : BaseResponse()

data class QuizQuizResponse (
    val result: List<QuizComment>
) : BaseResponse()