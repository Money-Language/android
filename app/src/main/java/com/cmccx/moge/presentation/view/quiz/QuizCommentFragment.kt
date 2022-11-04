package com.cmccx.moge.presentation.view.quiz

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.cmccx.moge.R
import com.cmccx.moge.base.getJwt
import com.cmccx.moge.databinding.FragmentQuizCommentBinding
import com.cmccx.moge.presentation.viewmodel.QuizViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.nio.file.Files.delete

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
        binding.commentContentsRv.adapter = QuizCommentAdapter(sharedViewModel.comments.value!!,
            object : QuizCommentAdapter.OptionsMoreClickListener {
                override fun onOptionsMoreClicked(view: View, position: Int) {
                    performOptionsMenuClick(view, position)
                }
            })

        binding.commentContentsRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

    }

    private fun performOptionsMenuClick(view: View, position: Int) {
        Log.d("TEST - 포지션", position.toString())
        
        val curComment = sharedViewModel.comments.value!![position]
        val popupMenu = PopupMenu(context, binding.commentContentsRv[position].findViewById(view.id))

        popupMenu.inflate(R.menu.comment_menu)

        popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                when (item?.itemId) {
                    R.id.comment_modify -> {
                        Toast.makeText(requireContext(), "수정", Toast.LENGTH_SHORT).show()
                        return true
                    }
                    R.id.comment_delete -> {
                        deleteComment(commentIdx= curComment.commentIdx)
                        return true
                    }
                    R.id.comment_child -> {
                        Toast.makeText(requireContext(), "대댓글", Toast.LENGTH_SHORT).show()
                        return true
                    }
                }
                return false
            }
        })
        popupMenu.show()
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

    private fun deleteComment(commentIdx: Int) {
        sharedViewModel.deleteQuizComment(
            jwt = getJwt(this.requireContext()),
            boardIdx = sharedViewModel.userBoard.value!!,
            commentIdx = commentIdx
        )
        sharedViewModel.getQuizComments(sharedViewModel.userBoard.value!!)
    }

}