package com.cmccx.moge.presentation.view.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cmccx.moge.data.remote.model.QuizCategory
import com.cmccx.moge.databinding.ItemBoardCardBinding
import com.cmccx.moge.presentation.viewmodel.HomeViewModel

class FavoriteCategoryAdapter(
    private val context: Context,
    private var categories: List<QuizCategory>,
    private val viewModel: HomeViewModel
) : RecyclerView.Adapter<FavoriteCategoryAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteCategoryAdapter.ViewHolder {
        val view = ItemBoardCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteCategoryAdapter.ViewHolder, position: Int) {
        val curItem = categories[position]

        with(holder) {
            cateName.text = curItem.categoryName
            cateSubName.text = curItem.categorySubName

            // 보드 호출
            cardRecyclerView.adapter = QuizBoardAdapter(viewModel)
            cardRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

    }

    override fun getItemCount(): Int {
        return categories.size
    }

    inner class ViewHolder(binding: ItemBoardCardBinding) : RecyclerView.ViewHolder(binding.root) {
        val cateName = binding.itemBoardCardCategoryTitleTv
        val cateSubName = binding.itemBoardCardCategorySubTv
        val cardRecyclerView = binding.itemBoardCardRv


    }
}