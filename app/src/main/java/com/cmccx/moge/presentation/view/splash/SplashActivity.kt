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
import com.cmccx.moge.presentation.view.MainActivity
import com.cmccx.moge.presentation.view.login.LoginActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private var jwt: String? = ""    // jwt

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        jwt = getJwt(this)

        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            if (jwt?.isNotEmpty() == true) {  // jwt 값이 있으면
                Log.d(TAG, "MainActivity 실행 / jwt = $jwt")
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            } else {    // jwt 값이 비어있으면 -> 로그인 또는 회원가입 진행해야함
                Log.d(TAG, "LoginActivity 실행 / jwt = $jwt")
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                finish()
            }

        }, 300)
    }
}