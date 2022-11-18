package com.cmccx.moge.presentation.view.myPage.myQuiz

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cmccx.moge.R
import com.cmccx.moge.data.remote.model.MyQuiz
import com.cmccx.moge.databinding.ItemMyQuizBoardsBinding

class MyQuizBoardAdapter(private val category: String) : RecyclerView.Adapter<MyQuizBoardAdapter.ViewHolder>() {
    private val quizBoardList = ArrayList<MyQuiz>()

    // 클릭 인터페이스
    interface QuizCateClickListener {
        fun onItemClick(myQuiz: MyQuiz)
    }

    private lateinit var quizCateClickListener: QuizCateClickListener

    fun setQuizCateClickListener(quizCateClickListener: QuizCateClickListener) {
        this.quizCateClickListener = quizCateClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyQuizBoardAdapter.ViewHolder {
        val binding : ItemMyQuizBoardsBinding = ItemMyQuizBoardsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyQuizBoardAdapter.ViewHolder, position: Int) {
        holder.bind(quizBoardList[position])

        holder.itemView.setOnClickListener {
            quizCateClickListener.onItemClick(quizBoardList[position])
        }
    }

    override fun getItemCount(): Int {
        return quizBoardList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addMyQuizCate(myQuiz: ArrayList<MyQuiz>) {
        this.quizBoardList.clear()
        this.quizBoardList.addAll(myQuiz)
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