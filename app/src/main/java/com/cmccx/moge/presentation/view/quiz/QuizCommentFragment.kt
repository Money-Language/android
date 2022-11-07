package com.cmccx.moge.presentation.view.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.cmccx.moge.base.getJwt
import com.cmccx.moge.databinding.FragmentQuizCommentBinding
import com.cmccx.moge.presentation.viewmodel.QuizViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class QuizCommentFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentQuizCommentBinding? = null
    protected val binding get() = _binding!!

    private val sharedViewModel: QuizViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuizCommentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = sharedViewModel

        sendCommentListener()

        // 업데이트
        sharedViewModel.comments.observe(this, Observer {
            binding.commentTotalPcsContentTv.text = it.size.toString()
            (binding.commentContentsRv.adapter as QuizCommentAdapter).setData(it)
        })
    }

    override fun onStart() {
        super.onStart()
        setAdapter()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun setAdapter() {
        binding.commentContentsRv.adapter = QuizCommentAdapter(this.requireContext(), sharedViewModel.comments.value!!, sharedViewModel)
        binding.commentContentsRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    private fun sendCommentListener() {
        // 댓글 등록
        binding.tempSendBtn.setOnClickListener {
            val content = binding.commentInputEt.text.toString()
            if (!content.isNullOrEmpty()) {

                // 퀴즈 등록
                sharedViewModel.postQuizComment(
                    jwt = getJwt(this.requireContext()),
                    boardIdx = sharedViewModel.userBoard.value!!,
                    content = content,
                    groupIdx = sharedViewModel.lastCommentGroupIdx,
                    parentIdx = 0
                    /** 임시 테스트 **/
                )
                // 퀴즈 재조회
                sharedViewModel.getQuizComments(sharedViewModel.userBoard.value!!)

            } else {
                Toast.makeText(this.requireContext(), "댓글을 입력해주세요", Toast.LENGTH_SHORT).show()
            }
        }
    }

}