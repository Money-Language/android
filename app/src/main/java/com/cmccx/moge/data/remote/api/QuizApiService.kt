package com.cmccx.moge.data.remote.api

import com.cmccx.moge.base.BaseResponse
import com.cmccx.moge.data.remote.model.*
import retrofit2.Call
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
    fun getQuizQuestion(
        @Path("boardIdx") boardIdx: Int
    ): Call<QuizQuestionResponse>

    // 퀴즈 보기 & 정답 조회 - 와니
    @GET("/app/boards/{boardIdx}/quiz")
    fun getQuizChoice(
        @Path("boardIdx") boardIdx: Int,
        @Query("quizIdx") quizIdx: Int
    ): Call<QuizChoiceResponse>

    // 퀴즈 정답 시 포인트 획득 - 와니
    @POST("/app/users/{userIdx}/get-points")
    suspend fun postQuizPoint(
        @Header("X-ACCESS-TOKEN") jwt : String,
        @Path("userIdx") userIdx: Int,
        @Body params: QuizPointPost
    ) : BaseResponse

    /** 댓글 **/
    // 퀴즈 댓글 조회 - 지니
    @GET("/app/boards/{boardIdx}/comments")
    suspend fun getQuizComments(
        @Path("boardIdx") boardIdx: Int,
    ): QuizCommentResponse

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