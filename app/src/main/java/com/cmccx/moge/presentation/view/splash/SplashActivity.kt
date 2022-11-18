package com.cmccx.moge.presentation.view.splash

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.cmccx.moge.base.getJwt
import com.cmccx.moge.base.getUserIdx
import com.cmccx.moge.databinding.ActivitySplashBinding
import com.cmccx.moge.presentation.view.MainActivity
import com.cmccx.moge.presentation.view.summary.SummaryActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    lateinit var binding : ActivitySplashBinding
    private var jwt: String? = ""    // jwt
    private var userIdx : Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        jwt = getJwt(this)
        userIdx = getUserIdx(this)

        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            if (jwt?.isNotEmpty() == true) {  // jwt 값이 있으면
                Log.d(TAG, "MainActivity 실행\njwt = $jwt\nuserIdx = $userIdx")
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {    // jwt 값이 비어있으면 -> 로그인 또는 회원가입 진행해야함
                Log.d(TAG, "SummaryActivity 실행\njwt = $jwt\nuserIdx = $userIdx")
                startActivity(Intent(this, SummaryActivity::class.java))
                finish()
            }
        }, 5500)
    }
}