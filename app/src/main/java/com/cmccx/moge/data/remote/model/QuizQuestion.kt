package com.cmccx.moge.data.remote.model

import com.google.gson.annotations.SerializedName

data class QuizQuestion(
    @SerializedName("quizIdx")      val quizIdx: Int,               // 퀴즈 인덱스
    @SerializedName("quizType")     val quizType: String,           // 퀴즈 타입 - 객관식, 주관식
    @SerializedName("question")     val quizQuestion: String        // 퀴즈 문제
)
