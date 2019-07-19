package com.kryptkode.footballfixtures.app.utils

import android.content.Context
import androidx.annotation.AttrRes
import androidx.core.content.res.getColorOrThrow

object AttrUtils {

    fun convertAttrToColor(@AttrRes attrResId: Int, context: Context): Int {
        val styledAttr = context.obtainStyledAttributes(null, intArrayOf(attrResId))
        val colorInt = styledAttr.getColorOrThrow(0)
        styledAttr.recycle()
        return colorInt
    }
}