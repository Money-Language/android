package com.cmccx.moge.presentation.viewmodel

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.viewpager2.widget.ViewPager2
import com.cmccx.moge.R
import com.cmccx.moge.data.remote.model.Quiz
import com.cmccx.moge.presentation.view.quiz.QuizAdapter

@BindingAdapter("quizData")
fun bindViewPager(
    viewPager2: ViewPager2,
    data: List<Quiz>?
) {
    val adapter = viewPager2.adapter as QuizAdapter?
    adapter?.submitList(data)

    viewPager2.orientation = ViewPager2.ORIENTATION_VERTICAL
    viewPager2.clipChildren = false
    viewPager2.offscreenPageLimit = 3
}

@BindingAdapter("setQuizChoice")
fun bindSetQuizChoice(view: TextView, choice: String) {
    view.text = choice
}

@BindingAdapter("setResultIconImg")
fun bindSetResultIconImg(view: ImageView, tryResult: QuizViewModel.QuizResult?) {
    when (tryResult) {
        QuizViewModel.QuizResult.CORRECT -> {
            view.setImageResource(R.drawable.icon_answer_right)
        }
        QuizViewModel.QuizResult.WRONG -> {
            view.setImageResource(R.drawable.icon_answer_wrong)
        }
        else -> {
            view.visibility = View.INVISIBLE
        }
    }
}

@BindingAdapter("setResultTxt")
fun bindSetResultTxt(view: TextView, isCorrect: Boolean) {
    if (isCorrect) {
        view.text = "정답입니다."
    } else {
        view.text = "오답입니다."
    }
}