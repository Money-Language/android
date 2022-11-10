package com.cmccx.moge.presentation.view.search

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cmccx.moge.R
import com.cmccx.moge.data.remote.model.TopTen
import com.cmccx.moge.databinding.ItemViewToptenBinding

class ViewTopTenAdapter(val context: Context) : RecyclerView.Adapter<ViewTopTenAdapter.ViewHolder>() {

    private val viewTopTenList = ArrayList<TopTen>()

    // 클릭 인터페이스
    interface ViewTopTenClickListener {
        fun onItemClick(topTen: TopTen)
    }

    private lateinit var viewTopTenClickListener: ViewTopTenClickListener

    fun setViewTopTenClickListener(viewTopTenClickListener: ViewTopTenClickListener) {
        this.viewTopTenClickListener = viewTopTenClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewTopTenAdapter.ViewHolder {
        val binding : ItemViewToptenBinding = ItemViewToptenBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewTopTenAdapter.ViewHolder, position: Int) {
        holder.bind(viewTopTenList[position])
        holder.binding.itemViewTopNumberTv.text = (position + 1).toString()
    }

    override fun getItemCount(): Int {
        return viewTopTenList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addTopTen(topTen: ArrayList<TopTen>) {
        this.viewTopTenList.clear()
        this.viewTopTenList.addAll(topTen)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemViewToptenBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(topTen : TopTen) {
            binding.itemViewTopTitleTv.text = topTen.title
            binding.itemViewTopInfoViewsCountTv.text = topTen.viewCount.toString()
            binding.itemViewTopInfoFavCountTv.text = topTen.likeCount.toString()
            Glide.with(context).load(topTen.profileImage).into(binding.itemViewTopProfileRiv)
            binding.itemViewTopProfileTv.text = topTen.nickname
            binding.itemViewTopCateTv.text = "#" + topTen.categoryName
            binding.itemViewTopQuizSizeTv.text = topTen.quizCount.toString() + "문제"

            // 카테고리에 따른 색상 변경
            when (topTen.categoryName) {
                "신조어" -> {
                    binding.itemViewTopNumberCl.setBackgroundResource(R.drawable.bg_search_num_new)
                    binding.itemViewTopCateTv.setBackgroundResource(R.drawable.bg_search_new)
                    binding.itemViewTopQuizSizeTv.setBackgroundResource(R.drawable.bg_search_new)
                }
                "맞춤법" -> {
                    binding.itemViewTopNumberCl.setBackgroundResource(R.drawable.bg_search_num_spelling)
                    binding.itemViewTopCateTv.setBackgroundResource(R.drawable.bg_search_spelling)
                    binding.itemViewTopQuizSizeTv.setBackgroundResource(R.drawable.bg_search_spelling)
                }
                "넌센스" -> {
                    binding.itemViewTopNumberCl.setBackgroundResource(R.drawable.bg_search_num_nonsense)
                    binding.itemViewTopCateTv.setBackgroundResource(R.drawable.bg_search_nonsense)
                    binding.itemViewTopQuizSizeTv.setBackgroundResource(R.drawable.bg_search_nonsense)
                }
                "단어 의미" -> {
                    binding.itemViewTopNumberCl.setBackgroundResource(R.drawable.bg_search_num_word)
                    binding.itemViewTopCateTv.setBackgroundResource(R.drawable.bg_search_word)
                    binding.itemViewTopQuizSizeTv.setBackgroundResource(R.drawable.bg_search_word)
                }
                "사자성어" -> {
                    binding.itemViewTopNumberCl.setBackgroundResource(R.drawable.bg_search_num_idiom)
                    binding.itemViewTopCateTv.setBackgroundResource(R.drawable.bg_search_idiom)
                    binding.itemViewTopQuizSizeTv.setBackgroundResource(R.drawable.bg_search_idiom)
                }
            }
        }
    }
}