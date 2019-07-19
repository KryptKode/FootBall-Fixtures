package com.kryptkode.footballfixtures

import android.os.Bundle
import android.util.TypedValue
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.lifecycle.Observer
import com.kryptkode.footballfixtures.app.base.activity.BaseViewModelActivity
import com.kryptkode.footballfixtures.app.navigator.Fragments
import com.kryptkode.footballfixtures.app.navigator.NavElements
import com.kryptkode.footballfixtures.app.navigator.Navigator
import com.kryptkode.footballfixtures.app.navigator.ToolbarUtils
import com.kryptkode.footballfixtures.databinding.ActivityMainBinding
import javax.inject.Inject

@Suppress("DEPRECATION")
class MainActivity : BaseViewModelActivity<ActivityMainBinding, MainViewModel>() {

    @Inject
    lateinit var fragments: Fragments

    private var navigator: Navigator? = null


    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            viewModel.handleNavItemSelected(item.itemId)
            true
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initNavigator()
        setUpBottomNav()
        initObservers()
        navigateToHome()
    }

    private fun initNavigator() {
        val toolbarUtils = ToolbarUtils(supportActionBar, binding.navView.menu)
        val navElements =
            NavElements(supportFragmentManager, fragments, binding.navView, toolbarUtils)
        navigator = Navigator(navElements)
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            navigateToHome()
        } else {
            viewModel.handleOnBackPressed()
        }
    }

    private fun navigateToHome() {
        viewModel.handleHomeNavigation()
    }


    private fun setUpBottomNav() {
        binding.navView.setTextVisibility(false)
        binding.navView.setIconSize(resources.getInteger(R.integer.bottom_nav_icon_size).toFloat())
        binding.navView.setIconsMarginTop(resources.getDimension(R.dimen.bottom_nav_icon_margin_top).toInt())
        val typedValue = TypedValue()
        val attr = intArrayOf(android.R.attr.actionBarSize)
        val typedArray = obtainStyledAttributes(typedValue.data, attr)
        val size = typedArray.getDimensionPixelSize(0, -1)
        binding.navView.setItemHeight(size)
        typedArray.recycle()
        binding.navView.enableShiftingMode(false)
        binding.navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    private fun initObservers() {
        viewModel.exit.observe(this, Observer {
            finish()
        })

        viewModel.showExitPrompt.observe(this, Observer {
            notifyUser(
                binding.root,
                getString(R.string.press_to_exit)
            )
        })

        viewModel.navigate.observe(this, Observer { destinaton ->
            navigator?.navigateTo(destinaton)
        })
    }


    override fun getLayoutResourceId() = R.layout.activity_main

    override fun getBindingVariable() = BR._all

    override fun getViewModelClass() = MainViewModel::class.java
}
