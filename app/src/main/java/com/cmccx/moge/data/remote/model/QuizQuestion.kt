package com.cmccx.moge.data.remote.model

import com.google.gson.annotations.SerializedName

data class QuizQuestion(
    @SerializedName("quizIdx")      val quizIdx: Int,               // 퀴즈 인덱스
    @SerializedName("quizStatus")   val quizType: String,           // 퀴즈 타입 - 객관식, 주관식
    @SerializedName("question")     val quizQuestion: String        // 퀴즈 문제


//    val boardIdx: Int,
//    val quizIdx: Int,
//    val quizType: String,          // 퀴즈 분류 - 객관식, 주관식
//    val question: String,          // 퀴즈 문제
//    val choiceHint: String,        // 주관식 힌트
//    val choiceFirst: String,       // 보기1
//    val choiceSecond: String,      // 보기2
//    val answer: String             // 정답
)
