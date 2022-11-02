package com.cmccx.moge.presentation.view.myPage.myQuiz

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cmccx.moge.R
import com.cmccx.moge.databinding.ItemMyQuizCateHeaderBinding

class QuizCateAdapter(
    private val context: Context,
    private val cateList: ArrayList<String>) : RecyclerView.Adapter<QuizCateAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemMyQuizCateHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val curItem = cateList[position]

        with (holder) {
            cate.text = curItem

            if (position == 0) {
                cate.setBackgroundResource(R.drawable.bg_gray_round_btn)
                cate.setTextColor(context.resources.getColor(R.color.primary_main))
            }
        }
    }

    override fun getItemCount(): Int {
        return cateList.size
    }

    inner class ViewHolder(binding: ItemMyQuizCateHeaderBinding) : RecyclerView.ViewHolder(binding.root) {
        val cate = binding.itemMyquizCateHeaderTitleTv
    }
}