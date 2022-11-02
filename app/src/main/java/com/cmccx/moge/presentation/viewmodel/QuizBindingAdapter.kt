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

@BindingAdapter("setNextQuizBtn")
fun bindSetNextQuizBtn(view: ConstraintLayout, tryStatus: QuizViewModel.QuizTry?) {
    when (tryStatus) {
        QuizViewModel.QuizTry.DONE -> {
            view.visibility = View.VISIBLE
        }
        else -> {
            view.visibility = View.GONE
        }
    }
}

@BindingAdapter("setEndQuizBtn")
fun bindSetEndQuizBtn(view: ConstraintLayout, tryStatus: QuizViewModel.QuizTry?) {
    when (tryStatus) {
        QuizViewModel.QuizTry.LAST -> {
            view.visibility = View.VISIBLE
        }
        else -> {
            view.visibility = View.GONE
        }
    }
}

@BindingAdapter("setQuizChoice")
fun bindSetQuizChoice(view: TextView, choice: String) {
    view.text = choice
}