package com.cmccx.moge.presentation.view.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cmccx.moge.databinding.ItemSearchToptenBinding

class LikeTopTenAdapter(private val likeTopTenList: ArrayList<String>) : RecyclerView.Adapter<LikeTopTenAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikeTopTenAdapter.ViewHolder {
        val view = ItemSearchToptenBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: LikeTopTenAdapter.ViewHolder, position: Int) {
        val curItem = likeTopTenList[position]

        with (holder) {
            number.text = curItem
        }
    }

    override fun getItemCount(): Int {
        return likeTopTenList.size
    }

    inner class ViewHolder(binding: ItemSearchToptenBinding) : RecyclerView.ViewHolder(binding.root) {
        val number = binding.itemSearchToptenNumberTv
    }
}