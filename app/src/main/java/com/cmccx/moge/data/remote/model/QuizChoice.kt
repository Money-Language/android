package com.cmccx.moge.data.remote.model

import com.google.gson.annotations.SerializedName

data class QuizChoice(
    @SerializedName("quizIdx")          val quizIdx: Int,               // 퀴즈 인덱스
    @SerializedName("content")          val content: String,            // 보기 인덱스
    @SerializedName("isAnswer")         val isAnswer: Int,              // 정답여부 -> 1: 정답 2: 오답
    @SerializedName("hint")             val hint: String                // 문제 힌트
)
