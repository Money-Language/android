package com.cmccx.moge.presentation.view.myPage.myQuiz

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cmccx.moge.R
import com.cmccx.moge.data.remote.model.MyQuiz
import com.cmccx.moge.databinding.ItemMyQuizBoardsBinding

class MyQuizAllAdapter() : RecyclerView.Adapter<MyQuizAllAdapter.ViewHolder>() {
    private val quizAllList = ArrayList<MyQuiz>()

    // 클릭 인터페이스
    interface QuizAllClickListener {
        fun onItemClick(myQuiz: MyQuiz)
    }

    private lateinit var quizAllClickListener: QuizAllClickListener

    fun setQuizAllClickListener(quizAllClickListener: QuizAllClickListener) {
        this.quizAllClickListener = quizAllClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyQuizAllAdapter.ViewHolder {
        val binding : ItemMyQuizBoardsBinding = ItemMyQuizBoardsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyQuizAllAdapter.ViewHolder, position: Int) {
        holder.bind(quizAllList[position])

        holder.itemView.setOnClickListener {
            quizAllClickListener.onItemClick(quizAllList[position])
        }
    }

    override fun getItemCount(): Int {
        return quizAllList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addMyQuizAll(myQuiz: ArrayList<MyQuiz>) {
        this.quizAllList.clear()
        this.quizAllList.addAll(myQuiz)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemMyQuizBoardsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(myQuiz: MyQuiz) {
            binding.itemMyquizBoardsCateTypeTv.text = myQuiz.categoryName
            binding.itemMyquizBoardsCateTitleTv.text = myQuiz.title
            binding.itemMyquizInfoPcsContentTv.text = myQuiz.quizCount.toString()
            binding.itemMyquizInfoFavContentTv.text = myQuiz.likeCount.toString()
            binding.itemMyquizInfoViewsContentTv.text = myQuiz.viewCount.toString()

            // 카테고리에 따른 색상 변경
            when (myQuiz.categoryName) {
                "신조어" -> binding.itemMyquizBoardsCateCl.setBackgroundResource(R.drawable.bg_board_cate_new)
                "맞춤법" -> binding.itemMyquizBoardsCateCl.setBackgroundResource(R.drawable.bg_board_cate_spelling)
                "넌센스" -> binding.itemMyquizBoardsCateCl.setBackgroundResource(R.drawable.bg_board_cate_nonsense)
                "단어 의미" -> binding.itemMyquizBoardsCateCl.setBackgroundResource(R.drawable.bg_board_cate_word)
                "사자성어" -> binding.itemMyquizBoardsCateCl.setBackgroundResource(R.drawable.bg_board_cate_idiom)
            }

        }
    }
}