package com.cmccx.moge.presentation.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.cmccx.moge.R
import com.cmccx.moge.databinding.ItemBoardContentBinding

class QuizAdapter() : RecyclerView.Adapter<QuizAdapter.ViewHolder>() {

    private val quizList = arrayListOf<String>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): QuizAdapter.ViewHolder {
        val view = ItemBoardContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuizAdapter.ViewHolder, position: Int) {
        val curItem = quizList[position]

        holder.quizTitle.text = curItem

        // 퀴즈 클릭 시 프래그먼트 이동
        holder.container.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_quizFragment)
        }

        holder.optionBtn.setOnClickListener {

        }
    }

    override fun getItemCount(): Int {
        return quizList.size
    }

    init {
        quizList.add("[dummy] 퀴즈1")
        quizList.add("[dummy] 퀴즈2")
        quizList.add("[dummy] 퀴즈3")
        quizList.add("[dummy] 퀴즈4")
        quizList.add("[dummy] 퀴즈5")
        quizList.add("[dummy] 퀴즈6")
        quizList.add("[dummy] 퀴즈7")
    }

    inner class ViewHolder(binding: ItemBoardContentBinding) : RecyclerView.ViewHolder(binding.root) {
        val container = binding.itemBoardCl
        val quizTitle = binding.itemBoardContentTitleTv
        val optionBtn = binding.itemBoardContentOptionImv
    }

}