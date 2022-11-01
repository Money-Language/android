package com.cmccx.moge.presentation.view.quiz

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cmccx.moge.R
import com.cmccx.moge.data.remote.model.Quiz
import com.cmccx.moge.databinding.ItemQuizCardBinding
import com.cmccx.moge.presentation.viewmodel.QuizViewModel

class QuizAdapter(
    val context: Context,
    val viewModel: QuizViewModel) :
    ListAdapter<Quiz, QuizAdapter.ViewHolder>(DiffCallback) {

    /**
     * viewModel 가져와서 visible 관리하자
     * **/

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): QuizAdapter.ViewHolder {
        val view = ItemQuizCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuizAdapter.ViewHolder, position: Int) {
        val curItem = getItem(position)
        holder.bind(curItem)

        with(holder) {
            // 처음 문제인 경우
            if (position == 0) {
                quizTryDate.visibility = View.VISIBLE
            // 마지막 문제 경우
            } else if (position == viewModel.quiz.value!!.size) {

            }

            // 퀴즈 인포 - 현재 퀴즈 넘버
            quizCur.text = (position + 1).toString()

            // 퀴즈 타입 - 객관식
            if (viewModel.quizType.value.toString() == "MULTI") {
                quizTypeMulti.visibility = View.VISIBLE
                quizTypeShort.visibility = View.GONE

                // 보기
                quizMFirstChoice.setOnClickListener {
                    it.setBackgroundColor(context.resources.getColor(R.color.divider_transfer))
                    viewModel.isTry(true)
                    /** 답 확인 로직 필요 **/
                    viewModel.isCorrect(true)

                    setMResult()
                }
                quizMSecondChoice.setOnClickListener {
                    it.setBackgroundColor(context.resources.getColor(R.color.divider_transfer))
                    viewModel.isTry(true)
                    /** 답 확인 로직 필요 **/
                    viewModel.isCorrect(false)

                    setMResult()
                }
            // 퀴즈 타입 - 주관식
            } else {
                quizTypeMulti.visibility = View.GONE
                quizTypeShort.visibility = View.VISIBLE

            }

            // 다음 문제로
            quizNextContainer.setOnClickListener {
                viewModel.plusCurPos()

                viewModel.getQuizChoiceFirst(boardIdx = viewModel.userBoard.value!!.toInt(), quizIdx=viewModel.curPosition)
                viewModel.getQuizChoiceSecond(boardIdx = viewModel.userBoard.value!!.toInt(), quizIdx=viewModel.curPosition)
                viewModel.getQuizAnswer(boardIdx = viewModel.userBoard.value!!.toInt(), quizIdx=viewModel.curPosition, quizChoiceIdx = "01")

                viewModel.makeQuiz(viewModel.curPosition)
            }


        }

    }

    inner class ViewHolder(private val binding: ItemQuizCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        // 최상단 푼 날짜
        val quizTryDate = binding.itemQuizCardTryDateTv

        // 퀴즈 타입
        val quizTypeMulti = binding.itemQuizCardMultiChoiceCl
        val quizTypeShort = binding.itemQuizCardShortChoiceCl

        // 문제 인덱스
        val quizCur = binding.itemQuizCardPcsCurrentTv

        // 보기
        val quizMFirstChoice = binding.itemQuizCardMultiChoiceFirstTv
        val quizMSecondChoice = binding.itemQuizCardMultiChoiceSecondTv

        // 풀이 결과
        val quizResultContainer = binding.itemQuizCardResultCl
        val quizResultPointIcon = binding.itemQuizCardResultGetPointIv
        val quizResultIcon = binding.itemQuizCardResultIconIv
        val quizResultText = binding.itemQuizCardResultTextTv

        // 다음 문제
        val quizNextContainer = binding.itemQuizBtmResultCl

        fun bind(quiz: Quiz) {
            binding.quiz = quiz
        }

        fun setMResult() {
            // 풀이 결과
            if (viewModel.tryStatus.value.toString() == "DONE") {
                quizResultContainer.visibility = View.VISIBLE

                // 맞춤
                if (viewModel.tryResult.value.toString() == "CORRECT") {
                    quizResultPointIcon.visibility = View.VISIBLE
                    quizResultIcon.setImageResource(R.drawable.icon_answer_right)
                    quizResultText.text = "정답입니다."
                    // 틀림
                } else {
                    quizResultPointIcon.visibility = View.INVISIBLE
                    quizResultIcon.setImageResource(R.drawable.icon_answer_wrong)
                    quizResultText.text = "오답입니다."
                }

                quizNextContainer.visibility = View.VISIBLE

            } else {
                quizResultContainer.visibility = View.INVISIBLE
                quizNextContainer.visibility = View.INVISIBLE
            }
        }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<Quiz>() {
        override fun areItemsTheSame(oldItem: Quiz, newItem: Quiz): Boolean {
            return oldItem.quizIdx == newItem.quizIdx
        }

        override fun areContentsTheSame(oldItem: Quiz, newItem: Quiz): Boolean {
            return oldItem.quizIdx == newItem.quizIdx
        }
    }
}