package com.cmccx.moge.presentation.view.myPage.myQuiz

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cmccx.moge.R
import com.cmccx.moge.databinding.ItemMyQuizCateHeaderBinding

class MyQuizCateAdapter(private val cateList: MutableList<String>) : RecyclerView.Adapter<MyQuizCateAdapter.ViewHolder>() {
    var selectPos = 0

    // 클릭 인터페이스
    interface CategoryClickListener {
        fun onItemClick(category: String)
    }

    private lateinit var categoryClickListener: CategoryClickListener

    fun setCategoryClickListener(categoryClickListener: CategoryClickListener) {
        this.categoryClickListener = categoryClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyQuizCateAdapter.ViewHolder {
        val binding : ItemMyQuizCateHeaderBinding = ItemMyQuizCateHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: MyQuizCateAdapter.ViewHolder, position: Int) {
        holder.bind(cateList[position])

        if(position == selectPos) {
            holder.binding.itemMyquizCateHeaderTitleTv.setBackgroundResource(R.drawable.bg_gray_round_btn)
            holder.binding.itemMyquizCateHeaderTitleTv.setTextColor(Color.WHITE)
        } else {
            holder.binding.itemMyquizCateHeaderTitleTv.setBackgroundResource(R.drawable.bg_gray_round_stroke_btn)
            holder.binding.itemMyquizCateHeaderTitleTv.setTextColor(Color.GRAY)
        }

        holder.itemView.setOnClickListener {
            val beforePos = selectPos
            selectPos = position

            notifyItemChanged(beforePos)
            notifyItemChanged(selectPos)

            categoryClickListener.onItemClick(cateList[position])
        }
    }

    override fun getItemCount(): Int {
        return cateList.size
    }

    inner class ViewHolder(val binding: ItemMyQuizCateHeaderBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(category: String) {
            binding.itemMyquizCateHeaderTitleTv.text = category
        }
    }
}