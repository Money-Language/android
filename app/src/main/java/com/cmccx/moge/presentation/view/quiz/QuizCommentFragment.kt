package com.cmccx.moge.presentation.view.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
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
       binding.commentContentsRv.adapter = QuizCommentAdapter(sharedViewModel.comment.value!!)
       binding.commentContentsRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

}