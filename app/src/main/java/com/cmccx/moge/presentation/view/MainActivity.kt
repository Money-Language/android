package com.cmccx.moge.presentation.view

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.cmccx.moge.base.ApplicationClass
import com.cmccx.moge.base.BaseActivity
import com.cmccx.moge.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment = supportFragmentManager.findFragmentById(binding.mainNavFcv.id) as NavHostFragment
        navController = navHostFragment.navController

        ApplicationClass.bottomNav = binding.mainNavBnv

        setupBottomNav()
    }

    private fun setupBottomNav() {
        val bottomNavigationView = binding.mainNavBnv
        bottomNavigationView.setupWithNavController(navController)
    }

}