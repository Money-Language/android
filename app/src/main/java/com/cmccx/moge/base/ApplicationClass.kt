package com.cmccx.moge.base

import android.app.Application

class ApplicationClass : Application() {
    // 테스트 서버 주소
    //val API_URL = ""

    // 실 서버 주소
    // val API_URL = ""

    companion object {

    }

    override fun onCreate() {
        super.onCreate()

        initApiSdk()
    }

    // 소셜 로그인 구현 시 활용하는 API SDK 초기화 하는 메서드
    private fun initApiSdk() {
        // Kakao SDK
        // KakaoSdk.init(this, BuildConfig.KAKAO_API_KEY)

        // Naver SDK
        //
    }
}