package com.cmccx.moge.presentation.view.quiz

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cmccx.moge.data.remote.model.Quiz
import com.cmccx.moge.databinding.ItemQuizCardBinding
import com.cmccx.moge.presentation.viewmodel.QuizViewModel

class QuizAdapter(val viewModel: QuizViewModel) : ListAdapter<Quiz, QuizAdapter.ViewHolder>(DiffCallback) {

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

        Log.d("STATUS-1", viewModel.tryStatus.value.toString())
        if (viewModel.tryStatus.value.toString() == "DONE") {
            Log.d("STATUS-", "ddddd")
            viewModel.isTry(true)
        }

        Log.d("STATUS-2", viewModel.tryStatus.value.toString())

        with(holder) {
            // 처음 문제인 경우
            if (position == 0) {
                quizTryDate.visibility = View.VISIBLE
            }

            quizCur.text = (position + 1).toString()
        }

    }

    inner class ViewHolder(private val binding: ItemQuizCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val quizTryDate = binding.itemQuizCardTryDateTv

        // 문제 인덱스
        val quizCur = binding.itemQuizCardPcsCurrentTv

        // 문제
        val quizQuestion = binding.itemQuizCardQuestionTv

        fun bind(quiz: Quiz) {
            binding.quiz = quiz
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