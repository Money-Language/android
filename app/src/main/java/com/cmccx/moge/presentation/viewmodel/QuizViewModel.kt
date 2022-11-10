package com.cmccx.moge.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.cmccx.moge.data.remote.api.QuizApiJinny
import com.cmccx.moge.data.remote.api.QuizApiWanny
import com.cmccx.moge.data.remote.model.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.w3c.dom.Comment

class QuizViewModel : ViewModel() {

    enum class QuizApiStatus { LOADING, ERROR, DONE }
    enum class QuizType { MULTI, SHORT } // 객관식, 주관식
    enum class QuizTry { DONE, YET, LAST }
    enum class QuizResult { CORRECT, WRONG }    // 정답 여부

    // api 연결 상태
    private val _apiStatus = MutableLiveData<QuizApiStatus>()
    val apiStatus: LiveData<QuizApiStatus> = _apiStatus

    // 퀴즈 타입
    private val _quizType = MutableLiveData<QuizType>()
    val quizType: LiveData<QuizType> = _quizType

    // 퀴즈 도전 상태
    private val _tryStatus = MutableLiveData<QuizTry>()
    val tryStatus: LiveData<QuizTry> = _tryStatus

    // 사용자의 정답 여부
    private val _tryResult = MutableLiveData<QuizResult>()
    val tryResult: LiveData<QuizResult> = _tryResult

    // 서버에서 받아오는 퀴즈 result 값
    private val _quizQuestion = MutableLiveData<List<QuizQuestion>>()
    val quizQuestion: LiveData<List<QuizQuestion>> = _quizQuestion

    private val _quizChoiceFirst = MutableLiveData<List<QuizChoice>>()
    val quizChoiceFirst: LiveData<List<QuizChoice>> = _quizChoiceFirst

    private val _quizChoiceSecond = MutableLiveData<List<QuizChoice>>()
    val quizChoiceSecond: LiveData<List<QuizChoice>> = _quizChoiceSecond

    private val _quizAnswer = MutableLiveData<List<QuizAnswer>>()
    val quizAnswer: LiveData<List<QuizAnswer>> = _quizAnswer

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

    // 사용자 선택
    private val _userBoard = MutableLiveData<Int>()
    val userBoard: LiveData<Int> = _userBoard
    private val _userQuiz = MutableLiveData<Int>()
    private val _userChoice = MutableLiveData<String>()


    init {
        Log.d("TEST", "QUIZVIEWMODEL 시작")
        /** !!! 보드 id 파셀라이즈 처리 해야함 !!! **/
        _userBoard.value = 1
        //getQuizComments(_userBoard.value!!)
        /** 임시!!!!! **/

        _tryStatus.value = QuizTry.YET

        getQuizQuestion(_userBoard.value!!)

        //makeQuiz(_curPosition)

    }

    fun setBoardIdx(input: Int) {
        _userBoard.value = input
    }

    fun plusCurPos() {
        _curPosition++
    }

    fun isTry(input: Boolean) {
        if (input) {
            _tryStatus.value = QuizTry.DONE
        } else {
            _tryStatus.value = QuizTry.YET
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

        getQuizChoiceFirst(boardIdx = userBoard.value!!.toInt(), quizIdx = _curPosition)
        getQuizChoiceSecond(boardIdx = userBoard.value!!.toInt(), quizIdx = _curPosition)
        getQuizAnswer(
            boardIdx = userBoard.value!!.toInt(),
            quizIdx = _curPosition,
            quizChoiceIdx = "01"
        )

        makeQuiz(_curPosition)

        if (_curPosition == _quiz.value!![0].quizTotal.toInt()) {
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
    fun getQuizQuestion(boardIdx: Int) {
        viewModelScope.launch {
            _apiStatus.value = QuizApiStatus.LOADING
            try {
                val response = QuizApiWanny.retrofitService.getQuizQuestion(boardIdx = boardIdx)
                _quizQuestion.value = response.result
                Log.d("TEST-문제", _quizQuestion.value.toString())

                // 보기 받아오기
                if (_quizQuestion.value!![_curPosition].quizType == "객관식") {
                    _quizType.value = QuizType.MULTI
                    getQuizChoiceFirst(boardIdx = boardIdx, quizIdx = _curPosition + 1)
                    getQuizChoiceSecond(boardIdx = boardIdx, quizIdx = _curPosition + 1)
                    getQuizAnswer(
                        boardIdx = boardIdx,
                        quizIdx = _curPosition + 1,
                        quizChoiceIdx = "01"
                    )
                } else {
                    _quizType.value = QuizType.SHORT
                    getQuizChoiceFirst(boardIdx = boardIdx, quizIdx = _curPosition + 1)
                    getQuizAnswer(
                        boardIdx = boardIdx,
                        quizIdx = _curPosition + 1,
                        quizChoiceIdx = "01"
                    )
                }

                _apiStatus.value = QuizApiStatus.DONE
            } catch (e: Exception) {
                Log.d("TEST-문제", e.toString())
                _apiStatus.value = QuizApiStatus.ERROR
            }
        }
    }

    // API 통신 -> 퀴즈 보기 가져오기
    private fun getQuizChoiceFirst(boardIdx: Int, quizIdx: Int) {
        viewModelScope.launch {
            _apiStatus.value = QuizApiStatus.LOADING
            try {
                val response = QuizApiWanny.retrofitService.getQuizChoice(
                    boardIdx = boardIdx,
                    quizIdx = quizIdx
                )
                _quizChoiceFirst.value = listOf(response.result[0])
                Log.d("TEST-보기1", response.result.toString())
                _apiStatus.value = QuizApiStatus.DONE
            } catch (e: Exception) {
                Log.d("TEST-보기1", e.toString())
                _apiStatus.value = QuizApiStatus.ERROR
            }
        }
    }

    // API 통신 -> 퀴즈 보기 가져오기
    private fun getQuizChoiceSecond(boardIdx: Int, quizIdx: Int) {
        viewModelScope.launch {
            _apiStatus.value = QuizApiStatus.LOADING
            try {
                val response = QuizApiWanny.retrofitService.getQuizChoice(
                    boardIdx = boardIdx,
                    quizIdx = quizIdx
                )
                _quizChoiceSecond.value = listOf(response.result[1])
                Log.d("TEST-보기2", response.result.toString())
                _apiStatus.value = QuizApiStatus.DONE
            } catch (e: Exception) {
                Log.d("TEST-보기2", e.toString())
                _apiStatus.value = QuizApiStatus.ERROR
            }
        }
    }

    // API 통신 -> 퀴즈 정답 가져오기
    private fun getQuizAnswer(boardIdx: Int, quizIdx: Int, quizChoiceIdx: String) {
        viewModelScope.launch {
            _apiStatus.value = QuizApiStatus.LOADING
            try {
                val response = QuizApiWanny.retrofitService.getQuizAnswer(
                    boardIdx = boardIdx,
                    quizIdx = quizIdx,
                    quizChoiceIdx = quizChoiceIdx
                )
                _quizAnswer.value = listOf(response.result[0])
                Log.d("TEST-정답", response.result.toString())
                _apiStatus.value = QuizApiStatus.DONE
            } catch (e: Exception) {
                Log.d("TEST-정답", e.toString())
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
                    content = content,        // 댓글 내용
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

    private fun makeQuiz(idx: Int) {
        viewModelScope.launch {
            delay(200)
            try {
                val test = Quiz(
                    boardIdx = 1,
                    quizIdx = idx + 1,
                    quizTotal = _quizQuestion.value!!.size.toString(),           // 퀴즈 총 갯수
                    quizType = _quizQuestion.value!![idx].quizType,              // 퀴즈 분류 - 객관식, 주관식
                    quizQuestion = _quizQuestion.value!![idx].quizQuestion,      // 퀴즈 문제
                    choiceHint = _quizChoiceFirst.value!![0].quizChoice,         // 주관식 힌트
                    choiceFirstIdx = _quizChoiceFirst.value!![0].choiceIdx,    // 보기1 인덱스
                    choiceFirst = _quizChoiceFirst.value!![0].quizChoice,        // 보기1
                    choiceSecondIdx = _quizChoiceSecond.value!![0].choiceIdx,  // 보기2 인덱스
                    choiceSecond = _quizChoiceSecond.value!![0].quizChoice,      // 보기2
                    quizAnswer = _quizAnswer.value!![0].quizAnswerValue           // 정답
                )
                _tempQuizArr.add(test)

                _quiz.value = _tempQuizArr
                Log.d("TEST-완성", test.toString())
            } catch (e: Exception) {
                Log.d("TEST-완성", e.toString())
            }
        }
    }

}