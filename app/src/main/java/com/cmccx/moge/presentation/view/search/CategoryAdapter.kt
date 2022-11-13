package com.cmccx.moge.presentation.view.search

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cmccx.moge.R
import com.cmccx.moge.databinding.ItemMyQuizCateHeaderBinding

class CategoryAdapter(private val context: Context, private val cateList: ArrayList<String>) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    var selectPos = 0

    // 클릭 인터페이스
    interface CategoryClickListener {
        fun onItemClick(category: String)
    }

    private lateinit var categoryClickListener: CategoryClickListener

    fun setCategoryClickListener(categoryClickListener: CategoryClickListener) {
        this.categoryClickListener = categoryClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.ViewHolder {
        val binding : ItemMyQuizCateHeaderBinding = ItemMyQuizCateHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: CategoryAdapter.ViewHolder, position: Int) {
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