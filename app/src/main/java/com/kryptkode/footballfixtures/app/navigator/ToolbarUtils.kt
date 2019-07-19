package com.kryptkode.footballfixtures.app.navigator

import android.view.Menu
import androidx.annotation.IdRes
import androidx.appcompat.app.ActionBar

class ToolbarUtils(private val actionBar: ActionBar?, private val menu: Menu) {

    fun setToolbarTitle(@IdRes itemId: Int) {
        actionBar?.title = menu.findItem(itemId).title
    }
}