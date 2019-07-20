package com.kryptkode.footballfixtures.app.views

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.kryptkode.footballfixtures.R
import com.kryptkode.footballfixtures.app.utils.AttrUtils


class PlaceHolderDrawable
/**
 * @param context application context
 */
    (context: Context) : CircularProgressDrawable(context) {
    init {
        strokeWidth = 5f
        centerRadius = 30f
        DrawableCompat.setTint(
            this,
            AttrUtils.convertAttrToColor(R.attr.colorAccent, context)
        )
        start()
    }
}
