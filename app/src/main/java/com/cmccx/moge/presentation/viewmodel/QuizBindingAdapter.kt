package com.cmccx.moge.presentation.viewmodel

import androidx.databinding.BindingAdapter
import androidx.viewpager2.widget.ViewPager2
import com.cmccx.moge.data.remote.model.QuizQuestion
import com.cmccx.moge.presentation.view.quiz.QuizAdapter

@BindingAdapter("quizData")
fun bindViewPager(viewPager2: ViewPager2,
                  data: List<QuizQuestion>?) {
    val adapter = viewPager2.adapter as QuizAdapter
    adapter.submitList(data)
}