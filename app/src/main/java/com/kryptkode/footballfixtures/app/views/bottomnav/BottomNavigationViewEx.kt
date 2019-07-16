package com.kryptkode.footballfixtures.app.views.bottomnav

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.floatingactionbutton.FloatingActionButton


@Suppress("DEPRECATION", "OverridingDeprecatedMember")
class BottomNavigationViewEx : BottomNavigationViewInner, CoordinatorLayout.AttachedBehavior {
    override fun getBehavior(): CoordinatorLayout.Behavior<*> {
        return FloatingActionButton.Behavior()
    }

    override val currentItem: Int
        get() {
            try {
                return super.currentItem
            } catch (e: Exception) {
                return 0
            }

        }

    override val onNavigationItemSelectedListener: OnNavigationItemSelectedListener?
        get() {
            try {
                return super.onNavigationItemSelectedListener
            } catch (e: Exception) {
                return null
            }

        }

    override val bottomNavigationMenuView: BottomNavigationMenuView
        get() = super.bottomNavigationMenuView

    override val bottomNavigationItemViews: Array<BottomNavigationItemView>?
        get() {
            try {
                return super.bottomNavigationItemViews
            } catch (e: Exception) {
                return null
            }

        }

    override val itemCount: Int
        get() {
            try {
                return super.itemCount
            } catch (e: Exception) {
                return 0
            }

        }

    override val itemHeight: Int
        get() {
            try {
                return super.itemHeight
            } catch (e: Exception) {
                return 0
            }

        }

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    override fun setIconVisibility(visibility: Boolean): BottomNavigationViewInner {
        try {
            return super.setIconVisibility(visibility)
        } catch (e: Exception) {
            return this
        }

    }

    override fun setTextVisibility(visibility: Boolean): BottomNavigationViewInner {
        try {
            return super.setTextVisibility(visibility)
        } catch (e: Exception) {
            return this
        }

    }

    override fun enableAnimation(enable: Boolean): BottomNavigationViewInner {
        try {
            return super.enableAnimation(enable)
        } catch (e: Exception) {
            return this
        }

    }

    override fun enableShiftingMode(enable: Boolean): BottomNavigationViewInner {
        try {
            return super.enableShiftingMode(enable)
        } catch (e: Exception) {
            return this
        }

    }

    override fun enableItemShiftingMode(enable: Boolean): BottomNavigationViewInner {
        try {
            return super.enableItemShiftingMode(enable)
        } catch (e: Exception) {
            return this
        }

    }

    override fun getMenuItemPosition(item: MenuItem): Int {
        try {
            return super.getMenuItemPosition(item)
        } catch (e: Exception) {
            return 0
        }

    }

    override fun setCurrentItem(index: Int): BottomNavigationViewInner {
        try {
            return super.setCurrentItem(index)
        } catch (e: Exception) {
            return this
        }

    }

    override fun setOnNavigationItemSelectedListener(listener: OnNavigationItemSelectedListener?) {
        try {
            super.setOnNavigationItemSelectedListener(listener)
        } catch (e: Exception) {
        }

    }

    override fun clearIconTintColor(): BottomNavigationViewInner {
        try {
            return super.clearIconTintColor()
        } catch (e: Exception) {
            return this
        }

    }

    @SuppressLint("RestrictedApi")
    override fun getBottomNavigationItemView(position: Int): BottomNavigationItemView {
        try {
            return super.getBottomNavigationItemView(position)
        } catch (e: Exception) {
            return BottomNavigationItemView(context)
        }

    }

    override fun getIconAt(position: Int): ImageView? {
        try {
            return super.getIconAt(position)
        } catch (e: Exception) {
            return null
        }

    }

    override fun getSmallLabelAt(position: Int): TextView? {
        try {
            return super.getSmallLabelAt(position)
        } catch (e: Exception) {
            return null
        }

    }

    override fun getLargeLabelAt(position: Int): TextView? {
        try {
            return super.getLargeLabelAt(position)
        } catch (e: Exception) {
            return null
        }

    }

    override fun setSmallTextSize(sp: Float): BottomNavigationViewInner {
        try {
            return super.setSmallTextSize(sp)
        } catch (e: Exception) {
            return this
        }

    }

    override fun setLargeTextSize(sp: Float): BottomNavigationViewInner {
        try {
            return super.setLargeTextSize(sp)
        } catch (e: Exception) {
            return this
        }

    }

    override fun setTextSize(sp: Float): BottomNavigationViewInner {
        try {
            return super.setTextSize(sp)
        } catch (e: Exception) {
            return this
        }

    }

    override fun setIconSizeAt(position: Int, width: Float, height: Float): BottomNavigationViewInner {
        try {
            return super.setIconSizeAt(position, width, height)
        } catch (e: Exception) {
            return this
        }

    }

    override fun setIconSize(width: Float, height: Float): BottomNavigationViewInner {
        try {
            return super.setIconSize(width, height)
        } catch (e: Exception) {
            return this
        }

    }

    override fun setIconSize(dpSize: Float): BottomNavigationViewInner {
        try {
            return super.setIconSize(dpSize)
        } catch (e: Exception) {
            return this
        }

    }

    override fun setItemHeight(height: Int): BottomNavigationViewInner {
        try {
            return super.setItemHeight(height)
        } catch (e: Exception) {
            return this
        }

    }

    override fun setTypeface(typeface: Typeface, style: Int): BottomNavigationViewInner {
        try {
            return super.setTypeface(typeface, style)
        } catch (e: Exception) {
            return this
        }

    }

    override fun setTypeface(typeface: Typeface): BottomNavigationViewInner {
        try {
            return super.setTypeface(typeface)
        } catch (e: Exception) {
            return this
        }

    }

    override fun setupWithViewPager(viewPager: ViewPager): BottomNavigationViewInner {
        try {
            return super.setupWithViewPager(viewPager)
        } catch (e: Exception) {
            return this
        }

    }

    override fun setupWithViewPager(viewPager: ViewPager?, smoothScroll: Boolean): BottomNavigationViewInner {
        try {
            return super.setupWithViewPager(viewPager, smoothScroll)
        } catch (e: Exception) {
            return this
        }

    }

    override fun enableShiftingMode(position: Int, enable: Boolean): BottomNavigationViewInner {
        try {
            return super.enableShiftingMode(position, enable)
        } catch (e: Exception) {
            return this
        }

    }

    override fun setItemBackground(position: Int, background: Int): BottomNavigationViewInner {
        try {
            return super.setItemBackground(position, background)
        } catch (e: Exception) {
            return this
        }

    }

    override fun setIconTintList(position: Int, tint: ColorStateList): BottomNavigationViewInner {
        try {
            return super.setIconTintList(position, tint)
        } catch (e: Exception) {
            return this
        }

    }

    override fun setTextTintList(position: Int, tint: ColorStateList): BottomNavigationViewInner {
        try {
            return super.setTextTintList(position, tint)
        } catch (e: Exception) {
            return this
        }

    }

    override fun setIconsMarginTop(marginTop: Int): BottomNavigationViewInner {
        try {
            return super.setIconsMarginTop(marginTop)
        } catch (e: Exception) {
            return this
        }

    }

    override fun setIconMarginTop(position: Int, marginTop: Int): BottomNavigationViewInner {
        try {
            return super.setIconMarginTop(position, marginTop)
        } catch (e: Exception) {
            return this
        }

    }
}