package com.cmccx.moge.data.remote.model

import com.google.gson.annotations.SerializedName

data class FromServerQuizQuestion(
    @SerializedName("quizIdx")          val quizIdx: Int,
    @SerializedName("quizStatus")       val quizType: String,           // 퀴즈 분류 - 객관식, 주관식
    @SerializedName("question")         val question: String,           // 퀴즈 문제
)
