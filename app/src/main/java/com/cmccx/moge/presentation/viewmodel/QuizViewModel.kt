package com.cmccx.moge.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.cmccx.moge.data.remote.api.QuizApiJinny
import com.cmccx.moge.data.remote.api.QuizApiWanny
import com.cmccx.moge.data.remote.model.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.w3c.dom.Comment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuizViewModel : ViewModel() {

    enum class QuizApiStatus { LOADING, ERROR, DONE }
    enum class QuizTry { DONE, YET, LAST }
    enum class QuizResult { CORRECT, WRONG }    // 정답 여부

    // api 연결 상태
    private val _apiStatus = MutableLiveData<QuizApiStatus>()
    val apiStatus: LiveData<QuizApiStatus> = _apiStatus

    // 퀴즈 도전 상태
    private val _tryStatus = MutableLiveData<QuizTry>()
    val tryStatus: LiveData<QuizTry> = _tryStatus

    // 사용자의 정답 여부
    private val _tryResult = MutableLiveData<QuizResult>()
    val tryResult: LiveData<QuizResult> = _tryResult

    // 서버에서 받아오는 퀴즈 result 값
    private val _quizQuestion = MutableLiveData<List<QuizQuestion>>()
    val quizQuestion: LiveData<List<QuizQuestion>> = _quizQuestion

    private val _quizChoice = MutableLiveData<List<QuizChoice>>()
    val quizChoice: LiveData<List<QuizChoice>> = _quizChoice

    // 댓글
    private val _comments = MutableLiveData<List<QuizComment>>()
    val comments: LiveData<List<QuizComment>> = _comments
    var lastCommentGroupIdx: Int = 0

    // 퀴즈
    private val _tempQuizArr = arrayListOf<Quiz>()
    private var _quiz = MutableLiveData<ArrayList<Quiz>>()
    val quiz: LiveData<ArrayList<Quiz>> = _quiz
    private var _curPosition = 0
    val curPosition get() = _curPosition
    private var _answerCount = 0
    val answerCount get() = _answerCount

    // 사용자 선택
    private val _userBoard = MutableLiveData<Int>()
    val userBoard: LiveData<Int> = _userBoard


    init {
        Log.d("TEST", "QUIZVIEWMODEL 시작")
        //getQuizComments(_userBoard.value!!)
        /** 임시!!!!! **/

        _tryStatus.value = QuizTry.YET

    }

    fun setBoardIdx(input: Int) {
        _userBoard.value = input
    }

    fun plusCurPos() {
        _curPosition++
    }

    fun plusAnswerCount() {
        _answerCount++
    }

    fun isTry(input: Boolean) {
        if (input) {
            _tryStatus.value = QuizTry.DONE
        } else {
            _tryStatus.value = QuizTry.YET
        }
    }

    fun isLast(input: Boolean) {
        if (input) {
            _tryStatus.value = QuizTry.LAST
        }
    }

    fun isCorrect(input: Boolean) {
        if (input) {
            _tryResult.value = QuizResult.CORRECT
        } else {
            _tryResult.value = QuizResult.WRONG
        }
    }

    fun getNextQuiz() {
        plusCurPos()

        makeQuiz(_curPosition)

        if (_curPosition == _quizQuestion.value!!.size) {
            _tryStatus.value = QuizTry.LAST
        } else {
            _tryStatus.value = QuizTry.YET
        }
    }

    // API 통신 -> 퀴즈 조회수 상승
    fun postQuizViews(jwt: String, boardIdx: Int) {
        viewModelScope.launch {
            _apiStatus.value = QuizApiStatus.LOADING
            try {
                QuizApiWanny.retrofitService.postQuizView(
                    jwt = jwt,
                    boardIdx = boardIdx
                )
                Log.d("TEST-조회수 증가", "성공")
                _apiStatus.value = QuizApiStatus.DONE
            } catch (e: Exception) {
                Log.d("TEST-조회수 증가", e.toString())
                _apiStatus.value = QuizApiStatus.ERROR
            }
        }
    }

    // API 통신 -> 퀴즈 문제 가져오기
    fun getQuizQuestion(result: QuizQuestionResult, choiceResult: QuizChoiceResult, boardIdx: Int) {
        QuizApiWanny.retrofitService.getQuizQuestion(boardIdx = boardIdx).enqueue(object :
            Callback<QuizQuestionResponse> {
                override fun onResponse(
                    call: Call<QuizQuestionResponse>,
                    response: Response<QuizQuestionResponse>
                ) {
                    if (response.isSuccessful) {
                        _quizQuestion.value = response.body()?.result
                        Log.d("TEST-문제", _quizQuestion.value.toString())
                        result.onGetQuizQuestionResultSuccess()
                    } else {
                        Log.d("TEST-문제", _quizQuestion.value.toString())
                    }
                }

                override fun onFailure(call: Call<QuizQuestionResponse>, t: Throwable) {
                    Log.d("TEST-문제", "${t.message}")
                }
            }
        )
    }

    // API 통신 -> 퀴즈 보기 & 정답 가져오기
    fun getQuizChoice(result: QuizChoiceResult?, boardIdx: Int, quizIdx: Int) {
        QuizApiWanny.retrofitService.getQuizChoice(boardIdx = boardIdx, quizIdx = quizIdx).enqueue(object :
            Callback<QuizChoiceResponse> {
            override fun onResponse(
                call: Call<QuizChoiceResponse>,
                response: Response<QuizChoiceResponse>
            ) {
                if (response.isSuccessful) {
                    _quizChoice.value = response.body()?.result
                    result?.onGetQuizChoiceResultSuccess()
                    Log.d("TEST-퀴즈보기", _quizChoice.value.toString())
                } else {
                    Log.d("TEST-퀴즈보기", _quizChoice.value.toString())
                }
            }

            override fun onFailure(call: Call<QuizChoiceResponse>, t: Throwable) {
                Log.d("TEST-퀴즈보기", "${t.message}")
            }
        }
        )
    }

    // API 통신 -> 정답 시 퀴즈 포인트 획득
    fun postQuizPoint(jwt: String, userIdx: Int, quizIdx:Int) {
        viewModelScope.launch {
            _apiStatus.value = QuizApiStatus.LOADING
            try {
                val params = QuizPointPost(
                    quizIdx = quizIdx
                )

                val request = QuizApiWanny.retrofitService.postQuizPoint(
                    jwt = jwt,
                    userIdx = userIdx,
                    params = params
                )
                Log.d("TEST-포인트 획득", request.message)
                _apiStatus.value = QuizApiStatus.DONE
            } catch (e: Exception) {
                Log.d("TEST-포인트 획득", e.toString())
                _apiStatus.value = QuizApiStatus.ERROR
            }
        }
    }

    // API 통신 -> 퀴즈 댓글 가져오기
    fun getQuizComments(boardIdx: Int) {
        viewModelScope.launch {
            _apiStatus.value = QuizApiStatus.LOADING
            try {
                val response = QuizApiJinny.retrofitService.getQuizComments(boardIdx = boardIdx)
                _comments.value = response.result
                lastCommentGroupIdx = _comments.value!![_comments.value!!.size-1].groupIdx + 1
                Log.d("TEST-댓글 조회", response.result.toString())
                _apiStatus.value = QuizApiStatus.DONE
            } catch (e: Exception) {
                Log.d("TEST-댓글 조회", e.toString())
                _apiStatus.value = QuizApiStatus.ERROR
            }
        }
    }

    // API 통신 -> 퀴즈 댓글 등록
    fun postQuizComment(
        jwt: String,
        boardIdx: Int,
        content: String,
        groupIdx: Int,
        parentIdx: Int
    ) {
        viewModelScope.launch {
            _apiStatus.value = QuizApiStatus.LOADING
            try {
                val params = QuizCommentPost(
                    groupIdx = groupIdx,          // 그룹 식별자
                    content = content,           // 댓글 내용
                    parentIdx = parentIdx        // 부모 식별자 -> 부모 : 0, 자녀(대댓글) 1
                )

                val request = QuizApiJinny.retrofitService.postQuizComment(
                    jwt = jwt,
                    boardIdx = boardIdx,
                    params = params
                )

                Log.d("TEST-댓글 등록", request.message)
                _apiStatus.value = QuizApiStatus.DONE
            } catch (e: Exception) {
                Log.d("TEST-댓글 등록", e.toString())
                _apiStatus.value = QuizApiStatus.ERROR
            }
        }
    }

    // API 통신 -> 퀴즈 댓글 삭제
    fun deleteQuizComment(
        jwt: String,
        boardIdx: Int,
        commentIdx: Int
    ) {
        viewModelScope.launch {
            _apiStatus.value = QuizApiStatus.LOADING
            try {
                val response = QuizApiJinny.retrofitService.deleteQuizComment(
                    jwt = jwt,
                    boardIdx = boardIdx,
                    commentIdx = commentIdx
                )

                Log.d("TEST-댓글 삭제", response.message)
                _apiStatus.value = QuizApiStatus.DONE
            } catch (e: Exception) {
                Log.d("TEST-댓글 삭제", e.toString())
                _apiStatus.value = QuizApiStatus.ERROR
            }
        }
    }

    fun makeQuiz(idx: Int) {
        // 객관식인 경우
        if (_quizQuestion.value!![idx].quizType == 1) {
            var answer: String = ""
            for (i in 0 until _quizChoice.value!!.size) {
                if (_quizChoice.value!![i].isAnswer == 1) {
                    answer = _quizChoice.value!![i].content
                }
            }

            val test = Quiz(
                boardIdx = _userBoard.value!!,
                quizIdx = _quizQuestion.value!![idx].quizIdx,
                quizQuestion = _quizQuestion.value!![idx].quizQuestion,      // 퀴즈 문제
                choiceHint = _quizChoice.value!![0].hint,                    // 주관식 힌트
                choiceFirst = _quizChoice.value!![0].content,        // 보기1
                choiceSecond = _quizChoice.value!![1].content,      // 보기2
                quizAnswer = answer           // 정답
            )
            _tempQuizArr.add(test)

            _quiz.value = _tempQuizArr
        } else {
            var answer: String = ""
            for (i in 0 until _quizChoice.value!!.size) {
                if (_quizChoice.value!![i].isAnswer == 1) {
                    answer = _quizChoice.value!![i].content
                }
            }

            val test = Quiz(
                boardIdx = _userBoard.value!!,
                quizIdx = _quizQuestion.value!![idx].quizIdx,
                quizQuestion = _quizQuestion.value!![idx].quizQuestion,      // 퀴즈 문제
                choiceHint = _quizChoice.value!![0].hint,                    // 주관식 힌트
                choiceFirst = _quizChoice.value!![0].content,        // 보기1
                choiceSecond = _quizChoice.value!![0].content,      // 보기2
                quizAnswer = answer           // 정답
            )
            _tempQuizArr.add(test)

            _quiz.value = _tempQuizArr
        }

        Log.d("TEST-퀴즈생성", _quiz.value!!.toString())
    }

}