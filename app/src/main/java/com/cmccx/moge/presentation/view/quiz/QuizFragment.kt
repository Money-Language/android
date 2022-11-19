package com.cmccx.moge.presentation.view.quiz

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.cmccx.moge.R
import com.cmccx.moge.base.BaseFragment
import com.cmccx.moge.base.getJwt
import com.cmccx.moge.data.remote.model.QuizChoiceResult
import com.cmccx.moge.data.remote.model.QuizQuestionResult
import com.cmccx.moge.databinding.FragmentQuizBinding
import com.cmccx.moge.presentation.view.MainActivity
import com.cmccx.moge.presentation.view.MainOwner
import com.cmccx.moge.presentation.viewmodel.PointViewModel
import com.cmccx.moge.presentation.viewmodel.QuizViewModel


class QuizFragment: BaseFragment<FragmentQuizBinding>(FragmentQuizBinding::bind, R.layout.fragment_quiz), QuizQuestionResult, QuizChoiceResult {

    private lateinit var owner: MainOwner
    private lateinit var mainActivity: MainActivity
    private val sharedViewModel: QuizViewModel by activityViewModels()
    private val pointViewModel: PointViewModel by activityViewModels()

    private val args by navArgs<QuizFragmentArgs>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        owner = context as MainOwner
        mainActivity = context as MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        owner.setBottomNavVisible(false)
        owner.setFloatingBtnVisible(false)

        sharedViewModel.setBoardIdx(args.boardData.boardIdx)
        sharedViewModel.getQuizComments(args.boardData.boardIdx)
        sharedViewModel.postQuizViews(getJwt(this.requireContext()), args.boardData.boardIdx)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        owner.setActionBarVisible(true)

        binding.lifecycleOwner = this
        binding.point = pointViewModel
        binding.viewModel = sharedViewModel

        binding.quizInfoQuizFavContentTv.text = args.boardData.likeCount

        binding.quizInfoQuizCommentCl.setOnClickListener {
            val bottomSheetDialog = QuizCommentFragment()
            bottomSheetDialog.show(mainActivity.supportFragmentManager, bottomSheetDialog.tag)
        }

        // 업데이트
        pointViewModel.point.observe(viewLifecycleOwner, Observer {
            binding.quizInfoUserPointContentTv.text = pointViewModel.point.value!!.toString()
        })

    }

    override fun onStop() {
        super.onStop()
        sharedViewModel.resetQuiz()
    }

    override fun onStart() {
        super.onStart()
        sharedViewModel.getQuizQuestion(this, this, args.boardData.boardIdx)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        owner.setBottomNavVisible(true)
        owner.setFloatingBtnVisible(true)
    }

    private fun setAdapter() {
        Log.d("TEST", "어댑터")
        binding.quizBoardVp.adapter = QuizAdapter(requireContext(), sharedViewModel.quizQuestion.value!!, sharedViewModel)
    }

    /** 퀴즈 보기 정답 조회 콜백 **/
    override fun onGetQuizChoiceResultSuccess() {
        sharedViewModel.makeQuiz(sharedViewModel.curPosition)
        Log.d("TEST-문제생성", sharedViewModel.quiz.value.toString())
        setAdapter()
    }

    override fun onGetQuizChoiceResultFailure(message: String) {
        TODO("Not yet implemented")
    }

    /** 퀴즈 문제 조회 콜백 **/
    override fun onGetQuizQuestionResultSuccess() {
        sharedViewModel.getQuizChoice(result = this, boardIdx = args.boardData.boardIdx, quizIdx = sharedViewModel.quizQuestion.value!![sharedViewModel.curPosition].quizIdx)
    }

    override fun onGetQuizQuestionResultFailure(message: String) {
        TODO("Not yet implemented")
    }

}