package com.cmccx.moge.presentation.view

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.cmccx.moge.R
import com.cmccx.moge.base.ApplicationClass
import com.cmccx.moge.base.BaseActivity
import com.cmccx.moge.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate), MainOwner {

    private lateinit var navController: NavController
    lateinit var floatingBtn: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment = supportFragmentManager.findFragmentById(binding.mainNavFcv.id) as NavHostFragment
        navController = navHostFragment.navController

        ApplicationClass.bottomNav = binding.mainNavBnv

        setupBottomNav()
        setupActionBar()
    }

    private fun setupBottomNav() {
        val bottomNavigationView = binding.mainNavBnv
        bottomNavigationView.setupWithNavController(navController)
    }

    // 액션 바 세팅
    private fun setupActionBar() {
        val toolbar = binding.mainActionbarTb
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment, R.id.searchFragment, R.id.shopFragment, R.id.myPageFragment))
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    // 네비게이션 뒤로 이동 세팅
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    // MainOwner interface에서 상속 -> Fragment에서 활용하기 위해, 액션 바 visible
    override fun setActionBarVisible(visible: Boolean) {
        val actionBar = supportActionBar
        if (visible) {
            actionBar!!.show()
        } else {
            actionBar!!.hide()
        }
    }

    // 바텀 네비 visible
    override fun setBottomNavVisible(visible: Boolean) {
        if (visible) {
            ApplicationClass.bottomNav.visibility = View.VISIBLE
        } else {
            ApplicationClass.bottomNav.visibility = View.GONE
        }
    }

    // 플로팅 버튼 visible
    override fun setFloatingBtnVisible(visible: Boolean) {
        val floatingBtn = binding.mainFloatingFab

        if (visible) {
            floatingBtn.visibility = View.VISIBLE
        } else {
            floatingBtn.visibility = View.GONE
        }
    }

}