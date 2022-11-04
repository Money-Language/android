package com.cmccx.moge.base

import android.app.Application
import android.view.View
import com.cmccx.moge.BuildConfig
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kakao.sdk.common.KakaoSdk
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApplicationClass : Application() {
    // 와니 테스트 서버 주소
    val W_API_URL = "https://dev.wani-softsquared.shop"
    // 지니 테스트 서버 주소
    val J_API_URL = "https://dev.ssacyj.shop"

    // 실 서버 주소
    // val API_URL = ""

    companion object {
        lateinit var sWannyRetrofit: Retrofit

        lateinit var bottomNav: BottomNavigationView

        // Retrofit
        lateinit var retrofitJ: Retrofit
    }

    override fun onCreate() {
        super.onCreate()

        initApiSdk()
        initWannyRetrofitInstance()
        initJinnyRetrofitInstance()
    }

    // 소셜 로그인 구현 시 활용하는 API SDK 초기화 하는 메서드
    private fun initApiSdk() {
        // Kakao SDK
        KakaoSdk.init(this, BuildConfig.kakao_app_key)

        // Naver SDK
        //
    }

    // 와니 서버 인스턴스 생성 메서드
    private fun initWannyRetrofitInstance() {
        val client: OkHttpClient = OkHttpClient.Builder()
            .readTimeout(5000, TimeUnit.MILLISECONDS)
            .connectTimeout(5000, TimeUnit.MILLISECONDS)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            //.addNetworkInterceptor(XAccessTokenInterceptor())
            .build()

        sWannyRetrofit = Retrofit.Builder()
            .baseUrl(W_API_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
            
    // 지니 서버 인스턴스 생성 메서드
    private fun initJinnyRetrofitInstance() {
        val clientJ: OkHttpClient = OkHttpClient.Builder()
            .readTimeout(5000, TimeUnit.MILLISECONDS)
            .connectTimeout(5000, TimeUnit.MILLISECONDS)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            //.addNetworkInterceptor(XAccessTokenInterceptor()) // JWT 자동 헤더 전송
            .build()

        retrofitJ = Retrofit.Builder()
            .baseUrl(J_API_URL)
            .client(clientJ)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

}