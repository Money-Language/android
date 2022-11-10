package com.cmccx.moge.presentation.view.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cmccx.moge.databinding.ItemSearchToptenBinding

class ViewTopTenAdapter(private val viewTopTenList: ArrayList<String>) : RecyclerView.Adapter<ViewTopTenAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewTopTenAdapter.ViewHolder {
        val view = ItemSearchToptenBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewTopTenAdapter.ViewHolder, position: Int) {
        val curItem = viewTopTenList[position]

        with (holder) {
            number.text = curItem
        }
    }

    override fun getItemCount(): Int {
        return viewTopTenList.size
    }

    inner class ViewHolder(binding: ItemSearchToptenBinding) : RecyclerView.ViewHolder(binding.root) {
        val number = binding.itemSearchToptenNumberTv
    }
}