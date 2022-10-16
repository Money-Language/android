package com.cmccx.moge.presentation.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cmccx.moge.databinding.ItemQuizCardBinding

class FavoriteCategoryAdapter(private val cateList: ArrayList<String>) : RecyclerView.Adapter<FavoriteCategoryAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteCategoryAdapter.ViewHolder {
        val view = ItemQuizCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteCategoryAdapter.ViewHolder, position: Int) {
        val curItem = cateList[position]
    }

    override fun getItemCount(): Int {
        return cateList.size
    }

    inner class ViewHolder(binding: ItemQuizCardBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}