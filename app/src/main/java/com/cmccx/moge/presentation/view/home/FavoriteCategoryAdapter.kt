package com.cmccx.moge.presentation.view.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cmccx.moge.data.remote.model.Quiz
import com.cmccx.moge.data.remote.model.QuizCategory
import com.cmccx.moge.databinding.ItemBoardCardBinding
import com.cmccx.moge.presentation.view.quiz.QuizAdapter
import com.cmccx.moge.presentation.viewmodel.HomeViewModel
import kotlinx.coroutines.NonDisposableHandle.parent

class FavoriteCategoryAdapter(
    private val context: Context,
    private val viewModel: HomeViewModel
) : ListAdapter<QuizCategory, FavoriteCategoryAdapter.ViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteCategoryAdapter.ViewHolder {
        val view = ItemBoardCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteCategoryAdapter.ViewHolder, position: Int) {
        val curItem = getItem(position)

        with(holder) {
            cateName.text = curItem.categoryName
            cateSubName.text = curItem.categorySubName

            // 보드 호출
            viewModel.getBoard(viewModel.cateIdx.value!!)

            cardRecyclerView.adapter = QuizBoardAdapter(viewModel)
            cardRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

    }

    inner class ViewHolder(binding: ItemBoardCardBinding) : RecyclerView.ViewHolder(binding.root) {
        val cateName = binding.itemBoardCardCategoryTitleTv
        val cateSubName = binding.itemBoardCardCategorySubTv
        val cardRecyclerView = binding.itemBoardCardRv
    }

    companion object DiffCallback : DiffUtil.ItemCallback<QuizCategory>() {
        override fun areItemsTheSame(oldItem: QuizCategory, newItem: QuizCategory): Boolean {
            return oldItem.categoryName == newItem.categoryName
        }

        override fun areContentsTheSame(oldItem: QuizCategory, newItem: QuizCategory): Boolean {
            return oldItem.categoryName == newItem.categoryName
        }
    }
}