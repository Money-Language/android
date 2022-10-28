package com.cmccx.moge.data.remote.api

import com.cmccx.moge.data.remote.model.FromServerQuizAnswer
import com.cmccx.moge.data.remote.model.FromServerQuizQuestion
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface QuizApiService {
    // 퀴즈 문제 조회
    @GET("/app/boards/{boardIdx}/quiz")
    suspend fun getQuizQuestion(
        @Path("boardIdx") boardIdx: Int
    ): List<FromServerQuizQuestion>

    // 퀴즈 보기, 정답 조회
    @GET("/app/boards/{boardIdx}/quiz?quizIdx=")
    suspend fun getQuizAnswer(
        @Path("boardIdx") boardIdx: Int,
        @Query("quizIdx") quizIdx: Int
    ): List<FromServerQuizAnswer>
}