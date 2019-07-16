package com.kryptkode.footballfixtures.app.views.bottomnav

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View

import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat


class BottomNavigationBehavior(context: Context, attrs: AttributeSet) : CoordinatorLayout.Behavior<BottomNavigationView>(context, attrs) {

    override fun layoutDependsOn(parent: CoordinatorLayout, child: BottomNavigationView, dependency: View): Boolean {
        if (dependency is Snackbar.SnackbarLayout) {
            this.updateSnackbar(child, (dependency as Snackbar.SnackbarLayout?)!!)
        }

        return super.layoutDependsOn(parent, child, dependency)
    }

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: BottomNavigationView, directTargetChild: View, target: View, axes: Int, type: Int): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onNestedPreScroll(coordinatorLayout: CoordinatorLayout, child: BottomNavigationView, target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
        child.translationY = Math.max(0.0f, Math.min(child.height.toFloat(), child.translationY + dy))
    }

    private fun updateSnackbar(child: BottomNavigationView, snackbarLayout: Snackbar.SnackbarLayout) {
        if (snackbarLayout.layoutParams is CoordinatorLayout.LayoutParams) {
            val layoutParams = snackbarLayout.layoutParams
                    ?: throw RuntimeException("null cannot be cast to non-null type android.support.design.widget.CoordinatorLayout.LayoutParams")

            val params = layoutParams as CoordinatorLayout.LayoutParams
            params.anchorId = child.id
            params.anchorGravity = Gravity.TOP
            params.gravity = Gravity.TOP
            snackbarLayout.layoutParams = params
        }

    }
}
