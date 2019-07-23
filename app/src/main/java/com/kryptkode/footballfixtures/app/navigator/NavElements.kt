package com.kryptkode.footballfixtures.app.navigator

import androidx.fragment.app.FragmentManager
import com.kryptkode.footballfixtures.app.views.bottomnav.BottomNavigationViewEx

data class NavElements(
    val supportFragmentManager: FragmentManager,
    val fragmentsFragments: Fragments,
    val navView: BottomNavigationViewEx,
    val toolbarUtils: ToolbarUtils
)