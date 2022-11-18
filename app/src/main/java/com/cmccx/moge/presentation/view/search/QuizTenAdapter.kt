package com.cmccx.moge.presentation.view.search

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cmccx.moge.R
import com.cmccx.moge.data.remote.model.Board
import com.cmccx.moge.databinding.ItemMyQuizBoardsBinding

class QuizTenAdapter(private val context: Context, private val category: String) : RecyclerView.Adapter<QuizTenAdapter.ViewHolder>() {

    private val quizTenList = ArrayList<Board>()

    private val limit = 10

    // 클릭 인터페이스
    interface QuizTenClickListener {
        fun onItemClick(board: Board)
    }

    private lateinit var quizTenClickListener: QuizTenClickListener

    fun setQuizTenClickListener(quizTenClickListener: QuizTenClickListener) {
        this.quizTenClickListener = quizTenClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizTenAdapter.ViewHolder {
        val binding : ItemMyQuizBoardsBinding = ItemMyQuizBoardsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuizTenAdapter.ViewHolder, position: Int) {
        holder.bind(quizTenList[position])

        holder.itemView.setOnClickListener {
            quizTenClickListener.onItemClick(quizTenList[position])
        }
    }

    override fun getItemCount(): Int {
        return if(quizTenList.size > limit) limit
        else quizTenList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addQuizTen(board: ArrayList<Board>) {
        this.quizTenList.clear()
        this.quizTenList.addAll(board)
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