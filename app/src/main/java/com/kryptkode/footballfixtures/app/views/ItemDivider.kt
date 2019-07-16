package com.kryptkode.footballfixtures.app.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable

import androidx.recyclerview.widget.RecyclerView

class ItemDivider// constructor loads built-in Android list item divider
(context: Context?) : RecyclerView.ItemDecoration() {
    private val divider: Drawable?


    init {
        val attrs = intArrayOf(android.R.attr.listDivider)
        val styledAttrs =context?.obtainStyledAttributes(attrs)
        divider = styledAttrs?.getDrawable(0)
        styledAttrs?.recycle()
    }

    // draws the list item dividers onto the RecyclerView
    override fun onDrawOver(c: Canvas, parent: RecyclerView,
                            state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

        // calculate left/right x-coordinates for all dividers
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        // for every item but the last, draw a line below it
        for (i in 0 until parent.childCount - 1) {
            val item = parent.getChildAt(i) // get ith list item

            // calculate top/bottom y-coordinates for current divider
            val top = item.bottom + (item.layoutParams as RecyclerView.LayoutParams).bottomMargin
            val bottom = top + divider!!.intrinsicHeight

            // draw the divider with the calculated bounds
            divider.setBounds(left, top, right, bottom)
            divider.draw(c)
        }
    }
}
