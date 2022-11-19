package com.cmccx.moge.presentation.view.quiz

import android.content.Context
import android.icu.lang.UCharacter.GraphemeClusterBreak.L
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cmccx.moge.R
import com.cmccx.moge.base.getJwt
import com.cmccx.moge.base.getUserIdx
import com.cmccx.moge.data.remote.model.Quiz
import com.cmccx.moge.data.remote.model.QuizQuestion
import com.cmccx.moge.databinding.ItemQuizCardBinding
import com.cmccx.moge.databinding.ItemQuizCardSingleBinding
import com.cmccx.moge.presentation.viewmodel.QuizViewModel

class QuizAdapter(
    val context: Context,
    var quizQuestion: List<QuizQuestion>,
    val viewModel: QuizViewModel) :
    ListAdapter<Quiz, RecyclerView.ViewHolder>(DiffCallback) {

    /**
     * viewModel 가져와서 visible 관리하자
     * **/

    object QuizViewType {
        const val MULTI = 1     // 객관식
        const val SINGLE = 0     // 주관식
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        if (viewModel.quizQuestion.value!![0].quizType == QuizViewType.MULTI) {
            return MultiViewHolder(
                ItemQuizCardBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
        return ShortViewHolder(
            ItemQuizCardSingleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (quizQuestion[position].quizType) {
            QuizViewType.MULTI -> {
                (holder as MultiViewHolder).bind(viewModel.quiz.value!![position], position)
            }
            else -> {
                (holder as ShortViewHolder).bind(viewModel.quiz.value!![position], position)
            }
        }
    }

    // 객관식
    inner class MultiViewHolder(private val binding: ItemQuizCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val curQuizCount= binding.itemQuizCardPcsCurrentTv
        val totalQuizCount = binding.itemQuizCardPcsTotalTv
        val question = binding.itemQuizCardQuestionTv

        val firstChoice = binding.itemQuizCardMultiChoiceFirstTv
        val secondChoice = binding.itemQuizCardMultiChoiceSecondTv

        // 풀이 결과
        val quizResultContainer = binding.itemQuizCardResultCl
        val quizResultPointIcon = binding.itemQuizCardResultGetPointIv
        val quizResultIcon = binding.itemQuizCardResultIconIv
        val quizResultText = binding.itemQuizCardResultTextTv

        fun bind(quiz: Quiz, position: Int) {
            // 초기화
            quizResultContainer.visibility = View.INVISIBLE

            curQuizCount.text = (position + 1).toString()
            totalQuizCount.text = viewModel.quizQuestion.value!!.size.toString()
            question.text = quiz.quizQuestion

            binding.quiz = quiz

            firstChoice.setOnClickListener {
                //it.setBackgroundResource(R.color.divider_transfer)
                //it.setBackgroundColor(context.resources.getColor(R.color.divider_transfer))
                viewModel.isTry(true)

                if (firstChoice.text == quiz.quizAnswer) {
                    viewModel.isCorrect(true)

                    quizResultContainer.visibility = View.VISIBLE
                    quizResultPointIcon.visibility = View.VISIBLE
                    quizResultIcon.setImageResource(R.drawable.icon_answer_right)
                    quizResultText.text = "정답입니다."

                    // 정답 처리
                    viewModel.plusAnswerCount()
                    viewModel.postQuizPoint(jwt = getJwt(context), userIdx = getUserIdx(context), quizIdx = quiz.quizIdx)
                } else {
                    viewModel.isCorrect(false)

                    quizResultContainer.visibility = View.VISIBLE
                    quizResultPointIcon.visibility = View.INVISIBLE
                    quizResultIcon.setImageResource(R.drawable.icon_answer_wrong)
                    quizResultText.text = "오답입니다."
                }

                if ( position < viewModel.quizQuestion.value!!.size -1 ) {
                    viewModel.getQuizChoice(result = null, boardIdx = viewModel.userBoard.value!!, quizIdx = viewModel.quizQuestion.value!![viewModel.curPosition+1].quizIdx)
                }
                else if (position == viewModel.quizQuestion.value!!.size - 1 ) {
                    viewModel.isLast(true)
                }
            }

            secondChoice.setOnClickListener {
                //it.setBackgroundResource(R.color.divider_transfer)
                //it.setBackgroundColor(context.resources.getColor(R.color.divider_transfer))
                viewModel.isTry(true)

                if (secondChoice.text == quiz.quizAnswer) {
                    viewModel.isCorrect(true)

                    quizResultContainer.visibility = View.VISIBLE
                    quizResultPointIcon.visibility = View.VISIBLE
                    quizResultIcon.setImageResource(R.drawable.icon_answer_right)
                    quizResultText.text = "정답입니다."

                    // 정답 처리
                    viewModel.plusAnswerCount()
                    viewModel.postQuizPoint(jwt = getJwt(context), userIdx = getUserIdx(context), quizIdx = quiz.quizIdx)
                } else {
                    viewModel.isCorrect(false)

                    quizResultContainer.visibility = View.VISIBLE
                    quizResultPointIcon.visibility = View.INVISIBLE
                    quizResultIcon.setImageResource(R.drawable.icon_answer_wrong)
                    quizResultText.text = "오답입니다."
                }

                if ( position < viewModel.quizQuestion.value!!.size - 1 ) {
                    viewModel.getQuizChoice(result = null, boardIdx = viewModel.userBoard.value!!, quizIdx = viewModel.quizQuestion.value!![viewModel.curPosition+1].quizIdx)
                }
                else if (position == viewModel.quizQuestion.value!!.size - 1 ) {
                    viewModel.isLast(true)
                }
            }

        }


    }

    inner class ShortViewHolder(private val binding: ItemQuizCardSingleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val curQuizCount= binding.itemQuizCardPcsCurrentTv
        val totalQuizCount = binding.itemQuizCardPcsTotalTv
        val question = binding.itemQuizCardQuestionTv

        val hint = binding.itemQuizCardShortChoiceHintTv
        val answer = binding.itemQuizCardShortChoiceAnswerEt
        val answerBtn = binding.itemQuizCardShortChoiceBtnIv

        // 풀이 결과
        val quizResultContainer = binding.itemQuizCardResultCl
        val quizResultPointIcon = binding.itemQuizCardResultGetPointIv
        val quizResultIcon = binding.itemQuizCardResultIconIv
        val quizResultText = binding.itemQuizCardResultTextTv

        fun bind(quiz: Quiz, position: Int) {
            // 초기화
            quizResultContainer.visibility = View.INVISIBLE

            curQuizCount.text = (position + 1).toString()
            totalQuizCount.text = viewModel.quizQuestion.value!!.size.toString()
            question.text = quiz.quizQuestion

            binding.quiz = quiz

            hint.setOnClickListener {
                hint.text = quiz.choiceHint
            }

            answerBtn.setOnClickListener {
                //it.setBackgroundResource(R.color.divider_transfer)
                //it.setBackgroundColor(context.resources.getColor(R.color.divider_transfer))
                viewModel.isTry(true)

                if (answer.text.toString() == quiz.quizAnswer) {
                    viewModel.isCorrect(true)

                    quizResultContainer.visibility = View.VISIBLE
                    quizResultPointIcon.visibility = View.VISIBLE
                    quizResultIcon.setImageResource(R.drawable.icon_answer_right)
                    quizResultText.text = "정답입니다."

                    // 정답 처리
                    viewModel.plusAnswerCount()
                    Log.d("TEST?????????????????", viewModel.answerCount.toString())
                    viewModel.postQuizPoint(jwt = getJwt(context), userIdx = getUserIdx(context), quizIdx = quiz.quizIdx)
                } else {
                    viewModel.isCorrect(false)

                    quizResultContainer.visibility = View.VISIBLE
                    quizResultPointIcon.visibility = View.INVISIBLE
                    quizResultIcon.setImageResource(R.drawable.icon_answer_wrong)
                    quizResultText.text = "오답입니다."
                }

                if ( position < viewModel.quizQuestion.value!!.size - 1 ) {
                    viewModel.getQuizChoice(result = null, boardIdx = viewModel.userBoard.value!!, quizIdx = viewModel.quizQuestion.value!![viewModel.curPosition+1].quizIdx)
                }
                else if (position == viewModel.quizQuestion.value!!.size - 1 ) {
                    viewModel.isLast(true)
                }
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