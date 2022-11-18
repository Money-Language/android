package com.cmccx.moge.presentation.view.search.result

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cmccx.moge.R
import com.cmccx.moge.data.remote.model.Search
import com.cmccx.moge.databinding.ItemMyQuizBoardsBinding

class SearchResultAdapter(private val context: Context) : RecyclerView.Adapter<SearchResultAdapter.ViewHolder>() {

    private val searchResultList = ArrayList<Search>()

    // 클릭 인터페이스
    interface SearchResultClickListener {
        fun onItemClick(search: Search)
    }

    private lateinit var searchResultClickListener: SearchResultClickListener

    fun setSearchResultClickListener(searchResultClickListener: SearchResultClickListener) {
        this.searchResultClickListener = searchResultClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultAdapter.ViewHolder {
        val binding : ItemMyQuizBoardsBinding = ItemMyQuizBoardsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchResultAdapter.ViewHolder, position: Int) {
        holder.bind(searchResultList[position])

        holder.itemView.setOnClickListener {
            searchResultClickListener.onItemClick(searchResultList[position])
        }
    }

    override fun getItemCount(): Int {
        return searchResultList.size;
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addSearchResult(board: ArrayList<Search>) {
        this.searchResultList.clear()
        this.searchResultList.addAll(board)
        notifyItemChanged(searchResultList.size)
    }

    inner class ViewHolder(val binding: ItemMyQuizBoardsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(search: Search) {
            binding.itemMyquizBoardsCateTypeTv.text = search.categoryName
            binding.itemMyquizBoardsCateTitleTv.text = search.title
            binding.itemMyquizInfoPcsContentTv.text = search.quizCount.toString()
            binding.itemMyquizInfoFavContentTv.text = search.likeCount.toString()
            binding.itemMyquizInfoViewsContentTv.text = search.viewCount.toString()
            binding.itemMyquizQuizBoardsCl.setBackgroundColor(context.getColor(R.color.bg_search_board))

            // 카테고리에 따른 색상 변경
            when (search.categoryName) {
                "신조어" -> binding.itemMyquizBoardsCateCl.setBackgroundResource(R.drawable.bg_board_cate_new)
                "맞춤법" -> binding.itemMyquizBoardsCateCl.setBackgroundResource(R.drawable.bg_board_cate_spelling)
                "넌센스" -> binding.itemMyquizBoardsCateCl.setBackgroundResource(R.drawable.bg_board_cate_nonsense)
                "단어 의미" -> binding.itemMyquizBoardsCateCl.setBackgroundResource(R.drawable.bg_board_cate_word)
                "사자성어" -> binding.itemMyquizBoardsCateCl.setBackgroundResource(R.drawable.bg_board_cate_idiom)
            }

        }
    }
}