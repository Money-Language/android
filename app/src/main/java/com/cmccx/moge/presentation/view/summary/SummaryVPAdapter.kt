package com.cmccx.moge.presentation.view.summary

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cmccx.moge.R
import com.cmccx.moge.databinding.ItemSummaryBinding

class SummaryVPAdapter(private val context: Context) : RecyclerView.Adapter<SummaryVPAdapter.ViewHolder>() {
    private val summaryList = ArrayList<Int>()

    init {
        summaryList.add(R.drawable.summary_1)
        summaryList.add(R.drawable.summary_2)
        summaryList.add(R.drawable.summary_3)
    }

    inner class ViewHolder(val binding: ItemSummaryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(image: Int) {
            Glide.with(context).load(image).into(binding.itemSummaryIv)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSummaryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return summaryList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(summaryList[position])
    }
}