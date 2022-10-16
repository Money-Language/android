package com.cmccx.moge.presentation.view.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.cmccx.moge.databinding.ItemQuizCardBinding

class FavoriteCategoryAdapter(
    private val context: Context,
    private val cateList: ArrayList<String>) : RecyclerView.Adapter<FavoriteCategoryAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteCategoryAdapter.ViewHolder {
        val view = ItemQuizCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteCategoryAdapter.ViewHolder, position: Int) {
        val curItem = cateList[position]

        with (holder) {
            cateName.text = curItem

            cardRecyclerView.adapter = QuizAdapter()
            cardRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

    }

    override fun getItemCount(): Int {
        return cateList.size
    }

    inner class ViewHolder(binding: ItemQuizCardBinding) : RecyclerView.ViewHolder(binding.root) {
        val cateName = binding.itemQuizCardCategoryTitleTv
        val cardRecyclerView = binding.itemQuizCardRv
    }
}