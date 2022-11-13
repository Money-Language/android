package com.cmccx.moge.presentation.view.search.viewAll

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cmccx.moge.R
import com.cmccx.moge.data.remote.model.Board
import com.cmccx.moge.databinding.ItemMyQuizBoardsBinding

class ViewAllAdapter(private val context: Context, private val category: String) : RecyclerView.Adapter<ViewAllAdapter.ViewHolder>() {

    private val viewAllList = ArrayList<Board>()

    // 클릭 인터페이스
    interface ViewAllClickListener {
        fun onItemClick(board: Board)
    }

    private lateinit var viewAllClickListener: ViewAllClickListener

    fun setViewAllClickListener(viewAllClickListener: ViewAllClickListener) {
        this.viewAllClickListener = viewAllClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewAllAdapter.ViewHolder {
        val binding : ItemMyQuizBoardsBinding = ItemMyQuizBoardsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewAllAdapter.ViewHolder, position: Int) {
        holder.bind(viewAllList[position])

        holder.itemView.setOnClickListener {
            viewAllClickListener.onItemClick(viewAllList[position])
        }
    }

    override fun getItemCount(): Int {
        return viewAllList.size;
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addViewAll(board: ArrayList<Board>) {
        this.viewAllList.clear()
        this.viewAllList.addAll(board)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemMyQuizBoardsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(board: Board) {
            binding.itemMyquizBoardsCateTypeTv.text = category
            binding.itemMyquizBoardsCateTitleTv.text = board.title
            binding.itemMyquizInfoPcsContentTv.text = board.quizCount.toString()
            binding.itemMyquizInfoFavContentTv.text = board.likeCount.toString()
            binding.itemMyquizInfoViewsContentTv.text = board.viewCount.toString()
            binding.itemMyquizQuizBoardsCl.setBackgroundColor(context.getColor(R.color.bg_search_board))

            // 카테고리에 따른 색상 변경
            when (category) {
                "신조어" -> binding.itemMyquizBoardsCateCl.setBackgroundResource(R.drawable.bg_board_cate_new)
                "맞춤법" -> binding.itemMyquizBoardsCateCl.setBackgroundResource(R.drawable.bg_board_cate_spelling)
                "넌센스" -> binding.itemMyquizBoardsCateCl.setBackgroundResource(R.drawable.bg_board_cate_nonsense)
                "단어 의미" -> binding.itemMyquizBoardsCateCl.setBackgroundResource(R.drawable.bg_board_cate_word)
                "사자성어" -> binding.itemMyquizBoardsCateCl.setBackgroundResource(R.drawable.bg_board_cate_idiom)
            }

        }
    }
}