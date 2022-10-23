package com.cmccx.moge.presentation.view.myPage.myQuiz

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cmccx.moge.databinding.ItemMyQuizBoardsBinding

class QuizBoardAdapter(private val quizBoardList: ArrayList<String>) : RecyclerView.Adapter<QuizBoardAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizBoardAdapter.ViewHolder {
        val view = ItemMyQuizBoardsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuizBoardAdapter.ViewHolder, position: Int) {
        val curItem = quizBoardList[position]

        with (holder) {
            cateType.text = curItem
        }
    }

    override fun getItemCount(): Int {
        return quizBoardList.size
    }

    inner class ViewHolder(binding: ItemMyQuizBoardsBinding) : RecyclerView.ViewHolder(binding.root) {
        val cateType = binding.itemMyquizBoardsCateTypeTv
        val cateTitle = binding.itemMyquizBoardsCateTitleTv
        val quizPcs = binding.itemMyquizInfoPcsContentTv
        val quizViews = binding.itemMyquizInfoViewsContentTv
        val quizFavs = binding.itemMyquizInfoFavContentTv
    }
}