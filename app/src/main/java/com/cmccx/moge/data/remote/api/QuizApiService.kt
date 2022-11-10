package com.cmccx.moge.data.remote.api

import com.cmccx.moge.base.BaseResponse
import com.cmccx.moge.data.remote.model.*
import retrofit2.http.*

interface QuizApiService {
    // 퀴즈 조회수
    @POST("/app/boards/{boardIdx}/view-count")
    suspend fun postQuizView(
        @Header("X-ACCESS-TOKEN") jwt : String,
        @Path("boardIdx") boardIdx: Int,
    ) : BaseResponse

    /** 퀴즈 풀이 **/
    // 퀴즈 문제 조회 - 와니
    @GET("/app/boards/{boardIdx}/quiz")
    suspend fun getQuizQuestion(
        @Path("boardIdx") boardIdx: Int
    ): QuizQuestionResponse

    // 퀴즈 보기 조회 - 와니
    @GET("/app/boards/{boardIdx}/quiz?")
    suspend fun getQuizChoice(
        @Path("boardIdx") boardIdx: Int,
        @Query("quizIdx") quizIdx: Int
    ): QuizChoiceResponse

    // 퀴즈 정답 조회
    @GET("/app/boards/{boardIdx}/quiz?")
    suspend fun getQuizAnswer(
        @Path("boardIdx") boardIdx: Int,
        @Query("quizIdx") quizIdx: Int,
        @Query("answerSelectIdx") quizChoiceIdx: String
    ): QuizAnswerResponse

    /** 댓글 **/
    // 퀴즈 댓글 조회 - 지니
    @GET("/app/boards/{boardIdx}/comments")
    suspend fun getQuizComments(
        @Path("boardIdx") boardIdx: Int,
    ): QuizQuizResponse

    // 퀴즈 댓글 등록 - 지니
    @POST("/app/boards/{boardIdx}/comments")
    suspend fun postQuizComment(
        @Header("X-ACCESS-TOKEN") jwt : String,
        @Path("boardIdx") boardIdx: Int,
        @Body params: QuizCommentPost
    ): BaseResponse

    // 퀴즈 댓글 삭제 - 지니
    @DELETE("/app/boards/{boardIdx}/comments/{commentIdx}")
    suspend fun deleteQuizComment(
        @Header("X-ACCESS-TOKEN") jwt : String,
        @Path("boardIdx") boardIdx: Int,
        @Path("commentIdx") commentIdx: Int
    ): BaseResponse
}