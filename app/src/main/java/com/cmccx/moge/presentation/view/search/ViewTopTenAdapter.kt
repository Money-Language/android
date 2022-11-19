package com.cmccx.moge.presentation.view.search

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cmccx.moge.R
import com.cmccx.moge.data.remote.model.Search
import com.cmccx.moge.databinding.ItemViewToptenBinding

class ViewTopTenAdapter(private val context: Context) : RecyclerView.Adapter<ViewTopTenAdapter.ViewHolder>() {

    private val viewTopTenList = ArrayList<Search>()

    // 클릭 인터페이스
    interface ViewTopTenClickListener {
        fun onItemClick(topTen: Search)
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

        holder.itemView.setOnClickListener {
            viewTopTenClickListener.onItemClick(viewTopTenList[position])
        }
    }

    override fun getItemCount(): Int {
        return viewTopTenList.size
    }

    fun addViewTopTen(topTen: ArrayList<Search>) {
        this.viewTopTenList.clear()
        this.viewTopTenList.addAll(topTen)
        notifyItemChanged(viewTopTenList.size)
    }

    inner class ViewHolder(val binding: ItemViewToptenBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(topTen : Search) {
            binding.itemViewTopTitleTv.text = topTen.title
            binding.itemViewTopInfoViewsCountTv.text = topTen.viewCount.toString()
            binding.itemViewTopInfoFavCountTv.text = topTen.likeCount.toString()
            binding.itemViewTopProfileTv.text = topTen.nickname
            binding.itemViewTopCateTv.text = "#" + topTen.categoryName
            binding.itemViewTopQuizSizeTv.text = topTen.quizCount.toString() + "문제"


            // 프로필 사진 여부에 따른 이미지 변경
            if (topTen.profileImage.isNullOrEmpty()) {
                binding.itemViewTopProfileRiv.setImageResource(R.drawable.icon_profile)
            } else {
                Glide.with(context).load(topTen.profileImage).into(binding.itemViewTopProfileRiv)
            }

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