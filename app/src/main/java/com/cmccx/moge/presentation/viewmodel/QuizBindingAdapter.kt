package com.cmccx.moge.presentation.viewmodel

import android.view.View
import android.widget.ImageView
import android.widget.TextView
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

@BindingAdapter("isGone")
fun bindIsGone(view: View, isGone: Boolean) {
    view.visibility = if (isGone) {
        View.GONE
    } else {
        View.VISIBLE
    }
}

@BindingAdapter("setQuizChoice")
fun bindSetQuizChoice(view: TextView, choice: String) {
    view.text = choice
}

@BindingAdapter("setResultIconImg")
fun bindSetResultIconImg(view: ImageView, isCorrect: Boolean) {
    if (isCorrect) {
        view.setImageResource(R.drawable.icon_answer_right)
    } else {
        view.setImageResource(R.drawable.icon_answer_wrong)
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