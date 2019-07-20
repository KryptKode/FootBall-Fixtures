package com.kryptkode.footballfixtures.app.views

import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import android.view.View

class StaggeredGridSpacingItemDecoration(
    private val spacing: Int,
    private val headerItemNoSpacing: Boolean
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {

        val spanCount = (parent.layoutManager as StaggeredGridLayoutManager).spanCount
        val layoutParams = view.layoutParams as StaggeredGridLayoutManager.LayoutParams

        if (layoutParams.isFullSpan) {
            if (headerItemNoSpacing)
                outRect.set(0, 0, 0, 0)
            else {
                view.setPadding(spacing, spacing, spacing, spacing)
                //outRect.top = spacing;
                //outRect.bottom = spacing;
                //outRect.left = spacing;
                //outRect.right = spacing;
            }
        } else {
            val spanIndex = layoutParams.spanIndex
            val layoutPosition = layoutParams.viewLayoutPosition
            val itemCount = parent.adapter!!.itemCount

            val leftEdge = spanIndex == 0
            val rightEdge = spanIndex == spanCount - 1

            val topEdge = spanIndex < spanCount
            val bottomEdge = layoutPosition >= itemCount - spanCount

            val halfSpacing = spacing / 2

            outRect.set(
                if (leftEdge) spacing else halfSpacing,
                if (topEdge) spacing else halfSpacing,
                if (rightEdge) spacing else halfSpacing,
                if (bottomEdge) spacing else 0
            )
        }
    }
}