package com.cmccx.moge.presentation.view.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.cmccx.moge.presentation.view.login.LoginActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            Log.d("splash", "loop")
            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            finish()
        }, 3000)
    }
}