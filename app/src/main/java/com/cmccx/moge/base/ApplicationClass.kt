package com.cmccx.moge.base

import android.app.Application
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApplicationClass : Application() {
    // 테스트 서버 주소
    val API_URL_J = "https://dev.ssacyj.shop"

    // 실 서버 주소
    // val API_URL = ""

    companion object {
        lateinit var bottomNav: BottomNavigationView

        // Retrofit
        lateinit var retrofitJ: Retrofit
    }

    override fun onCreate() {
        super.onCreate()

        initApiSdk()
        initRetrofitInstance()
    }

    // 소셜 로그인 구현 시 활용하는 API SDK 초기화 하는 메서드
    private fun initApiSdk() {
        // Kakao SDK
        // KakaoSdk.init(this, BuildConfig.KAKAO_API_KEY)

        // Naver SDK
        //
    }

    // Retrofit 의 각종 설정값들 지정
    private fun initRetrofitInstance() {
        val clientJ: OkHttpClient = OkHttpClient.Builder()
            .readTimeout(5000, TimeUnit.MILLISECONDS)
            .connectTimeout(5000, TimeUnit.MILLISECONDS)
            // 로그캣에 okhttp.OkHttpClient로 검색하면 http 통신 내용을 보여줍니다.
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            //.addNetworkInterceptor(XAccessTokenInterceptor()) // JWT 자동 헤더 전송
            .build()

        // sRetrofit 이라는 전역변수에 API url, 인터셉터, Gson을 넣어주고 빌드해주는 코드
        // 이 전역변수로 http 요청을 서버로 보내면 됩니다.
        retrofitJ = Retrofit.Builder()
            .baseUrl(API_URL_J)
            .client(clientJ)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

}