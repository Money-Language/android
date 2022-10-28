package com.cmccx.moge.data.remote.model

data class Quiz(
    val boardIdx: Int,
    val quizIdx: Int,
    val quizType: String,          // 퀴즈 분류 - 객관식, 주관식
    val question: String,          // 퀴즈 문제
    val choiceHint: String,        // 주관식 힌트
    val choiceFirst: String,       // 보기1
    val choiceSecond: String,      // 보기2
    val answer: String             // 정답
)
