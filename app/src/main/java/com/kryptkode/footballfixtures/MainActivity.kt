package com.kryptkode.footballfixtures

import android.os.Bundle
import android.util.TypedValue
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import com.kryptkode.footballfixtures.app.views.bottomnav.BottomNavigationViewEx

class MainActivity : AppCompatActivity() {

    private lateinit var textMessage: TextView
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                textMessage.setText(R.string.title_home)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                textMessage.setText(R.string.title_dashboard)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationViewEx = findViewById(R.id.nav_view)

        textMessage = findViewById(R.id.message)
        navView.setTextVisibility(false)
        navView.setIconSize(resources.getInteger(R.integer.bottom_nav_icon_size).toFloat())
        navView.setIconsMarginTop(resources.getDimension(R.dimen.bottom_nav_icon_margin_top).toInt())
        val typedValue = TypedValue()
        val attr = intArrayOf(android.R.attr.actionBarSize)
        val typedArray = obtainStyledAttributes(typedValue.data, attr)
        val size =typedArray.getDimensionPixelSize(0, -1)
        navView.setItemHeight(size)
        typedArray.recycle()
        navView.enableShiftingMode(false)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }
}
