package com.cmccx.moge.presentation.view.quiz

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.cmccx.moge.R
import com.cmccx.moge.base.BaseFragment
import com.cmccx.moge.databinding.FragmentQuizBinding
import com.cmccx.moge.presentation.view.MainActivity
import com.cmccx.moge.presentation.view.MainOwner
import com.cmccx.moge.presentation.viewmodel.QuizViewModel


class QuizFragment: BaseFragment<FragmentQuizBinding>(FragmentQuizBinding::bind, R.layout.fragment_quiz) {

    private lateinit var owner: MainOwner
    private lateinit var mainActivity: MainActivity
    private val sharedViewModel: QuizViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        owner = context as MainOwner
        mainActivity = context as MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        owner.setBottomNavVisible(false)
        owner.setFloatingBtnVisible(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        owner.setActionBarVisible(true)

        binding.lifecycleOwner = this
        binding.viewModel = sharedViewModel

        binding.quizInfoQuizCommentCl.setOnClickListener {
            val bottomSheetDialog = QuizCommentFragment()
            bottomSheetDialog.show(mainActivity.supportFragmentManager, bottomSheetDialog.tag)
        }
    }

    override fun onStart() {
        super.onStart()
        setAdapter()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        owner.setBottomNavVisible(true)
        owner.setFloatingBtnVisible(true)
    }

    private fun setAdapter() {
        binding.quizBoardVp.adapter = QuizAdapter(requireContext(), sharedViewModel)
    }
}