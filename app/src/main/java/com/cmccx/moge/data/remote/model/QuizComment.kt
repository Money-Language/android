package com.cmccx.moge.data.remote.model

data class QuizComment(
    val groupIdx: Int,          // 그룹 식별자
    val content: String,        // 댓글 내용
    val parentIdx: Int,         // 부모 식별자 -> 부모 : 0, 자녀(대댓글) 1
    val elapsedTime: String,    // 시간 (작성 이후 흐른 시간)
    val nickname: String,       // 닉네임
    val profileImage: String,   // 프로필사진
    val commentCount: Int,      // 댓글수
    val commentLike: Int        // 댓글 좋아요 수
)
data class QuizCommentPost(
    val groupIdx: Int,          // 그룹 식별자
    val content: String,        // 댓글 내용
    val parentIdx: Int,         // 부모 식별자 -> 부모 : 0, 자녀(대댓글) 1
)