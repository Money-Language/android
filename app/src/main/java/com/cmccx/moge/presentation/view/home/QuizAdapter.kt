package com.cmccx.moge.presentation.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cmccx.moge.databinding.ItemQuizContentBinding

class QuizAdapter() : RecyclerView.Adapter<QuizAdapter.ViewHolder>() {

    private val quizList = arrayListOf<String>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): QuizAdapter.ViewHolder {
        val view = ItemQuizContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuizAdapter.ViewHolder, position: Int) {
        val curItem = quizList[position]

        holder.quizTitle.text = curItem

        holder.optionBtn.setOnClickListener {

        }
    }

    override fun getItemCount(): Int {
        return quizList.size
    }

    init {
        quizList.add("[dummy] 퀴즈1")
        quizList.add("[dummy] 퀴즈2")
        quizList.add("[dummy] 퀴즈3")
        quizList.add("[dummy] 퀴즈4")
        quizList.add("[dummy] 퀴즈5")
        quizList.add("[dummy] 퀴즈6")
        quizList.add("[dummy] 퀴즈7")
    }

    inner class ViewHolder(binding: ItemQuizContentBinding) : RecyclerView.ViewHolder(binding.root) {
        val quizTitle = binding.itemQuizContentTitleTv
        val optionBtn = binding.itemQuizContentOptionImv
    }
}