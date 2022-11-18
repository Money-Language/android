package com.cmccx.moge.presentation.view.search.result

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cmccx.moge.data.remote.model.Keyword
import com.cmccx.moge.databinding.ItemSearchKeywordBinding

class SearchKeywordAdapter() : RecyclerView.Adapter<SearchKeywordAdapter.ViewHolder>() {

    private val keywordList = ArrayList<Keyword>()

    // 클릭 인터페이스
    interface KeywordClickListener {
        fun onItemClick(keyword: Keyword)
    }

    private lateinit var keywordClickListener: KeywordClickListener

    fun setKeywordClickListener(keywordClickListener: KeywordClickListener) {
        this.keywordClickListener = keywordClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchKeywordAdapter.ViewHolder {
        val binding : ItemSearchKeywordBinding = ItemSearchKeywordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchKeywordAdapter.ViewHolder, position: Int) {
        holder.bind(keywordList[position])

        holder.itemView.setOnClickListener {
            keywordClickListener.onItemClick(keywordList[position])
        }
    }

    override fun getItemCount(): Int {
        return keywordList.size;
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addKeywordResult(keyword: ArrayList<Keyword>) {
        this.keywordList.clear()
        this.keywordList.addAll(keyword)
        notifyItemChanged(keywordList.size)
    }

    inner class ViewHolder(val binding: ItemSearchKeywordBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(keyword: Keyword) {
            binding.itemSearchKeywordTv.text = keyword.keyword
        }
    }
}