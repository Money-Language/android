package com.cmccx.moge.presentation.view.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.cmccx.moge.R
import com.cmccx.moge.data.remote.model.QuizBoard
import com.cmccx.moge.databinding.ItemBoardContentBinding
import com.cmccx.moge.presentation.viewmodel.HomeViewModel

class QuizBoardAdapter(private val viewModel: HomeViewModel) :
    RecyclerView.Adapter<QuizBoardAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): QuizBoardAdapter.ViewHolder {
        val view =
            ItemBoardContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuizBoardAdapter.ViewHolder, position: Int) {
        val curItem = viewModel.quizBoard.value!![position]

        with(holder) {
            nickName.text = curItem.nickname
            elapsedTime.text = curItem.elapsedTime

            quizTitle.text = curItem.title
            viewCount.text = curItem.viewCount
            likeCount.text = curItem.likeCount
        }

        // 퀴즈 클릭 시 프래그먼트 이동
        holder.container.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToQuizFragment(curItem)
            it.findNavController().navigate(action)
        }

        holder.optionBtn.setOnClickListener {

        }
    }

    override fun getItemCount(): Int {
        return viewModel.quizBoard.value!!.size
    }

    inner class ViewHolder(binding: ItemBoardContentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val container = binding.itemBoardCl
        val nickName = binding.itemBoardContentProfileNameTv

        // 프로필이미지
        val elapsedTime = binding.itemBoardContentProfileUploadTv
        val quizTitle = binding.itemBoardContentTitleTv
        val viewCount = binding.itemBoardContentViewTv
        val likeCount = binding.itemBoardContentLikeTv
        val optionBtn = binding.itemBoardContentOptionImv
    }

}