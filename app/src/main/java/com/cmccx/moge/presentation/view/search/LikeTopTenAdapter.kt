package com.cmccx.moge.presentation.view.search

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cmccx.moge.R
import com.cmccx.moge.data.remote.model.TopTen
import com.cmccx.moge.databinding.ItemFavToptenBinding

class LikeTopTenAdapter(private val context: Context) : RecyclerView.Adapter<LikeTopTenAdapter.ViewHolder>() {

    private val likeTopTenList = ArrayList<TopTen>()

    // 클릭 인터페이스
    interface LikeTopTenClickListener {
        fun onItemClick(topTen: TopTen)
    }

    private lateinit var likeTopTenClickListener: LikeTopTenClickListener

    fun setLikeTopTenClickListener(likeTopTenClickListener: LikeTopTenClickListener) {
        this.likeTopTenClickListener = likeTopTenClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikeTopTenAdapter.ViewHolder {
        val binding : ItemFavToptenBinding = ItemFavToptenBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: LikeTopTenAdapter.ViewHolder, position: Int) {
        holder.bind(likeTopTenList[position])
        holder.binding.itemFavTopNumberTv.text = (position + 1).toString()
    }

    override fun getItemCount(): Int {
        return likeTopTenList.size
    }

    fun addLikeTopTen(topTen: ArrayList<TopTen>) {
        this.likeTopTenList.clear()
        this.likeTopTenList.addAll(topTen)
        notifyItemChanged(likeTopTenList.size)
    }

    inner class ViewHolder(val binding: ItemFavToptenBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(topTen : TopTen) {
            binding.itemFavTopTitleTv.text = topTen.title
            binding.itemFavTopInfoViewsCountTv.text = topTen.viewCount.toString()
            binding.itemFavTopInfoFavCountTv.text = topTen.likeCount.toString()
            binding.itemFavTopProfileTv.text = topTen.nickname
            binding.itemFavTopCateTv.text = "#" + topTen.categoryName
            binding.itemFavTopQuizSizeTv.text = topTen.quizCount.toString() + "문제"

            // 프로필 사진 여부에 따른 이미지 변경
            if (topTen.profileImage.isNullOrEmpty()) {
                binding.itemFavTopProfileRiv.setImageResource(R.drawable.icon_profile)
            } else {
                Glide.with(context).load(topTen.profileImage).into(binding.itemFavTopProfileRiv)
            }

            // 카테고리에 따른 색상 변경
            when (topTen.categoryName) {
                "신조어" -> {
                    binding.itemFavTopNumberCl.setBackgroundResource(R.drawable.bg_search_num_new)
                    binding.itemFavTopCateTv.setBackgroundResource(R.drawable.bg_search_new)
                    binding.itemFavTopQuizSizeTv.setBackgroundResource(R.drawable.bg_search_new)
                }
                "맞춤법" -> {
                    binding.itemFavTopNumberCl.setBackgroundResource(R.drawable.bg_search_num_spelling)
                    binding.itemFavTopCateTv.setBackgroundResource(R.drawable.bg_search_spelling)
                    binding.itemFavTopQuizSizeTv.setBackgroundResource(R.drawable.bg_search_spelling)
                }
                "넌센스" -> {
                    binding.itemFavTopNumberCl.setBackgroundResource(R.drawable.bg_search_num_nonsense)
                    binding.itemFavTopCateTv.setBackgroundResource(R.drawable.bg_search_nonsense)
                    binding.itemFavTopQuizSizeTv.setBackgroundResource(R.drawable.bg_search_nonsense)
                }
                "단어 의미" -> {
                    binding.itemFavTopNumberCl.setBackgroundResource(R.drawable.bg_search_num_word)
                    binding.itemFavTopCateTv.setBackgroundResource(R.drawable.bg_search_word)
                    binding.itemFavTopQuizSizeTv.setBackgroundResource(R.drawable.bg_search_word)
                }
                "사자성어" -> {
                    binding.itemFavTopNumberCl.setBackgroundResource(R.drawable.bg_search_num_idiom)
                    binding.itemFavTopCateTv.setBackgroundResource(R.drawable.bg_search_idiom)
                    binding.itemFavTopQuizSizeTv.setBackgroundResource(R.drawable.bg_search_idiom)
                }
            }
        }
    }
}