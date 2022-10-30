package com.cmccx.moge.presentation.view.quiz

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cmccx.moge.data.remote.model.QuizQuestion
import com.cmccx.moge.databinding.ItemQuizCardBinding

class QuizAdapter() : ListAdapter<QuizQuestion, QuizAdapter.ViewHolder>(DiffCallback){

    private val dummy = arrayListOf<String>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): QuizAdapter.ViewHolder {
        val view = ItemQuizCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuizAdapter.ViewHolder, position: Int) {
        val curItem = dummy[position]

        with (holder) {
            // 데이터 세팅
            quizQ.text = curItem

            // 처음 문제인 경우
            if (position == 0) {
                date.visibility = View.VISIBLE
            }
            // 마지막 문제고, 문제를 푼 상태인 경우
            if (position == dummy.size - 1) {
                last.visibility = View.VISIBLE
            }
        }

    }

    override fun getItemCount(): Int {
        return dummy.size
    }

    init {
        dummy.add("1번 dummy 문제")
        dummy.add("2번 dummy 문제")
        dummy.add("3번 dummy 문제")
        dummy.add("4번 dummy 문제")
        dummy.add("5번 dummy 문제")

    }

    inner class ViewHolder(binding: ItemQuizCardBinding) : RecyclerView.ViewHolder(binding.root) {
        val date = binding.itemQuizCardTryDateTv
        val last = binding.itemQuizBtmResultLastCl

        val quizQ = binding.itemQuizCardQuestionTv


        val test = binding.itemQuizCardContainerCl
    }

    companion object DiffCallback : DiffUtil.ItemCallback<QuizQuestion>() {
        override fun areItemsTheSame(oldItem: QuizQuestion, newItem: QuizQuestion): Boolean {
            return oldItem.quizIdx == newItem.quizIdx
        }

        override fun areContentsTheSame(oldItem: QuizQuestion, newItem: QuizQuestion): Boolean {
            return oldItem.quizIdx == newItem.quizIdx
        }
    }
}