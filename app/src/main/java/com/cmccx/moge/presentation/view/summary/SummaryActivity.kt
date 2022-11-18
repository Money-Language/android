package com.cmccx.moge.presentation.view.summary

import android.content.Intent
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.cmccx.moge.base.BaseActivity
import com.cmccx.moge.databinding.ActivitySummaryBinding
import com.cmccx.moge.presentation.view.login.LoginActivity

class SummaryActivity : BaseActivity<ActivitySummaryBinding>(ActivitySummaryBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()

        binding.summaryHeaderTv.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun initView() {
        binding.summaryVp.adapter = SummaryVPAdapter(this)
        binding.summaryVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.summaryCi.setViewPager(binding.summaryVp)
    }

}