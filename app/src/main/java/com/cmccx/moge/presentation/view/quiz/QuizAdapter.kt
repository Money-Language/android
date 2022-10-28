package com.cmccx.moge.presentation.view.quiz

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cmccx.moge.databinding.ItemBoardCardBinding
import com.cmccx.moge.databinding.ItemQuizCardBinding

class QuizAdapter(
    private val context: Context) : RecyclerView.Adapter<QuizAdapter.ViewHolder>() {

    private val dummy = arrayListOf<String>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): QuizAdapter.ViewHolder {
        val view = ItemQuizCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuizAdapter.ViewHolder, position: Int) {
        val curItem = dummy[position]

//        var choiceStatus -> enum class
        var tryStatus = true

        with (holder) {
            // 데이터 세팅
            quizQ.text = curItem

            // 처음 문제인 경우
            if (position == 0) {
                date.visibility = View.VISIBLE
            }
            // 마지막 문제고, 문제를 푼 상태인 경우
            else if (position == dummy.size - 1 && tryStatus) {
                last.visibility = View.VISIBLE
            }
        }

    }

    override fun getItemCount(): Int {
        return dummy.size
    }

    init {
        dummy.add("1번 dummy 문제")
        dummy.add("2번 dummy 문제")
        dummy.add("3번 dummy 문제")
        dummy.add("4번 dummy 문제")
        dummy.add("5번 dummy 문제")

    }

    inner class ViewHolder(binding: ItemQuizCardBinding) : RecyclerView.ViewHolder(binding.root) {
        val date = binding.itemQuizCardTryDateTv
        val last = binding.itemQuizBtmResultLastCl

        val quizQ = binding.itemQuizCardQuestionTv
    }
}