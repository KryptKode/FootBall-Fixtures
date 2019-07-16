package com.kryptkode.footballfixtures.app.views.bottomnav

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Paint
import android.graphics.Typeface

import android.util.AttributeSet
import android.util.SparseIntArray
import android.util.TypedValue
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.ViewPager

import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.google.android.material.internal.ThemeEnforcement
import com.kryptkode.footballfixtures.R

import java.lang.ref.WeakReference

/**
 * Created by yu on 2016/11/10.
 */
@Suppress("LeakingThis")
@SuppressLint("RestrictedApi")
open class BottomNavigationViewInner @SuppressLint("PrivateResource")
constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    BottomNavigationView(context, attrs, defStyleAttr) {
    // used for animation
    private var mShiftAmount: Float = 0.toFloat()
    private var mScaleUpFactor: Float = 0.toFloat()
    private var mScaleDownFactor: Float = 0.toFloat()
    private var animationRecord: Boolean = false
    private var mLargeLabelSize: Float = 0.toFloat()
    private var mSmallLabelSize: Float = 0.toFloat()
    private var visibilityTextSizeRecord: Boolean = false
    private var visibilityHeightRecord: Boolean = false
    private var mItemHeight: Int = 0
    private var textVisibility = true
    // used for animation end

    // used for setupWithViewPager
    private var mViewPager: ViewPager? = null
    private var mMyOnNavigationItemSelectedListener: MyOnNavigationItemSelectedListener? = null
    private var mPageChangeListener: BottomNavigationViewExOnPageChangeListener? = null
    private var mMenuView: BottomNavigationMenuView? = null
    private var mButtons: Array<BottomNavigationItemView>? = null

    /**
     * get the current checked item position
     *
     * @return index of item, start from 0.
     */
    open/*
        1. get field in this class
        private final BottomNavigationMenuView mMenuView;

        2. get field in mMenuView
        private BottomNavigationItemView[] mButtons;

        3. get menu and traverse it to get the checked one
         */// 2. get mButtons
    // 3. get menu and traverse it to get the checked one
    val currentItem: Int
        get() {
            val mButtons = bottomNavigationItemViews
            val menu = menu
            for (i in mButtons!!.indices) {
                if (menu.getItem(i).isChecked) {
                    return i
                }
            }
            return 0
        }

    /**
     * get OnNavigationItemSelectedListener
     *
     * @return
     */
    open// private OnNavigationItemSelectedListener mListener;
    val onNavigationItemSelectedListener: OnNavigationItemSelectedListener?
        get() = getField<OnNavigationItemSelectedListener>(
            BottomNavigationView::class.java,
            this,
            "selectedListener"
        )

    /**
     * get private mMenuView
     *
     * @return
     */
    open val bottomNavigationMenuView: BottomNavigationMenuView
        get() {
            if (null == mMenuView)
                mMenuView = getField<BottomNavigationMenuView>(BottomNavigationView::class.java, this, "menuView")
            return mMenuView!!
        }

    /**
     * get private mButtons in mMenuView
     *
     * @return
     */
    open/*
         * 1 private final BottomNavigationMenuView mMenuView;
         * 2 private BottomNavigationItemView[] mButtons;
         */ val bottomNavigationItemViews: Array<BottomNavigationItemView>?
        get() {
            if (null != mButtons)
                return mButtons
            val mMenuView = bottomNavigationMenuView
            mButtons = getField<Array<BottomNavigationItemView>>(mMenuView.javaClass, mMenuView, "buttons")
            return mButtons
        }

    /**
     * return item count
     *
     * @return
     */
    open val itemCount: Int
        get() {
            val bottomNavigationItemViews = bottomNavigationItemViews ?: return 0
            return bottomNavigationItemViews.size
        }

    /**
     * get menu item height
     *
     * @return in px
     */
    open// 1. get mMenuView
    // 2. get private final int mItemHeight in mMenuView
    val itemHeight: Int
        get() {
            val mMenuView = bottomNavigationMenuView
            return getField<Int>(mMenuView.javaClass, mMenuView, "itemHeight")!!
        }

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null) : this(context, attrs, 0) {
    }

    init {
        val a = ThemeEnforcement.obtainTintedStyledAttributes(
            context, attrs,
            R.styleable.BottomNavigationView,
            defStyleAttr, R.style.Widget_Design_BottomNavigationView,
            R.styleable.BottomNavigationView_itemTextAppearanceInactive,
            R.styleable.BottomNavigationView_itemTextAppearanceActive
        )
        // clear if you don't have set item icon tint list
        if (!a.hasValue(R.styleable.BottomNavigationView_itemIconTint)) {
            clearIconTintColor()
        }
        a.recycle()
    }

    /**
     * change the visibility of icon
     *
     * @param visibility
     */
    open fun setIconVisibility(visibility: Boolean): BottomNavigationViewInner {
        /*
        1. get field in this class
        private final BottomNavigationMenuView mMenuView;

        2. get field in mButtons
        private BottomNavigationItemView[] mButtons;

        3. get mIcon in mButtons
        private ImageView mIcon

        4. set mIcon visibility gone

        5. change mItemHeight to only text size in mMenuView
         */
        // 1. get mMenuView
        val mMenuView = bottomNavigationMenuView
        // 2. get mButtons
        val mButtons = bottomNavigationItemViews
        // 3. get mIcon in mButtons
        for (button in mButtons!!) {
            val mIcon = getField<ImageView>(button.javaClass, button, "icon")
            // 4. set mIcon visibility gone
            mIcon!!.visibility = if (visibility) View.VISIBLE else View.INVISIBLE
        }

        // 5. change mItemHeight to only text size in mMenuView
        if (!visibility) {
            // if not record mItemHeight
            if (!visibilityHeightRecord) {
                visibilityHeightRecord = true
                mItemHeight = itemHeight
            }

            // change mItemHeight
            val button = mButtons[0]
            val mIcon = getField<ImageView>(button.javaClass, button, "icon")
            //                System.out.println("mIcon.getMeasuredHeight():" + mIcon.getMeasuredHeight());
            mIcon?.post {
                //                            System.out.println("mIcon.getMeasuredHeight():" + mIcon.getMeasuredHeight());
                setItemHeight(mItemHeight - mIcon.measuredHeight)
            }
        } else {
            // if not record the mItemHeight, we need do nothing.
            if (!visibilityHeightRecord)
                return this

            // restore it
            setItemHeight(mItemHeight)
        }

        mMenuView.updateMenuView()
        return this
    }

    /**
     * change the visibility of text
     *
     * @param visibility
     */
    open fun setTextVisibility(visibility: Boolean): BottomNavigationViewInner {
        this.textVisibility = visibility
        /*
        1. get field in this class
        private final BottomNavigationMenuView mMenuView;

        2. get field in mButtons
        private BottomNavigationItemView[] mButtons;

        3. set text size in mButtons
        private final TextView mLargeLabel
        private final TextView mSmallLabel

        4. change mItemHeight to only icon size in mMenuView
         */
        // 1. get mMenuView
        val mMenuView = bottomNavigationMenuView
        // 2. get mButtons
        val mButtons = bottomNavigationItemViews

        // 3. change field mShiftingMode value in mButtons
        for (button in mButtons!!) {
            val mLargeLabel = getField<TextView>(button.javaClass, button, "largeLabel")
            val mSmallLabel = getField<TextView>(button.javaClass, button, "smallLabel")

            if (!visibility) {
                // if not record the font size, record it
                if (!visibilityTextSizeRecord && !animationRecord) {
                    visibilityTextSizeRecord = true
                    mLargeLabelSize = mLargeLabel!!.textSize
                    mSmallLabelSize = mSmallLabel!!.textSize
                }

                // if not visitable, set font size to 0
                mLargeLabel!!.setTextSize(TypedValue.COMPLEX_UNIT_PX, 0f)
                mSmallLabel!!.setTextSize(TypedValue.COMPLEX_UNIT_PX, 0f)

            } else {
                // if not record the font size, we need do nothing.
                if (!visibilityTextSizeRecord)
                    break

                // restore it
                mLargeLabel!!.setTextSize(TypedValue.COMPLEX_UNIT_PX, mLargeLabelSize)
                mSmallLabel!!.setTextSize(TypedValue.COMPLEX_UNIT_PX, mSmallLabelSize)
            }
        }

        // 4 change mItemHeight to only icon size in mMenuView
        if (!visibility) {
            // if not record mItemHeight
            if (!visibilityHeightRecord) {
                visibilityHeightRecord = true
                mItemHeight = itemHeight
            }

            // change mItemHeight to only icon size in mMenuView
            // private final int mItemHeight;

            // change mItemHeight
            //            System.out.println("mLargeLabel.getMeasuredHeight():" + getFontHeight(mSmallLabelSize));
            setItemHeight(
                mItemHeight - getFontHeight(
                    mSmallLabelSize
                )
            )

        } else {
            // if not record the mItemHeight, we need do nothing.
            if (!visibilityHeightRecord)
                return this
            // restore mItemHeight
            setItemHeight(mItemHeight)
        }

        mMenuView.updateMenuView()
        return this
    }

    /**
     * enable or disable click item animation(text scale and icon move animation in no item shifting mode)
     *
     * @param enable It means the text won't scale and icon won't move when active it in no item shifting mode if false.
     */
    open fun enableAnimation(enable: Boolean): BottomNavigationViewInner {
        /*
        1. get field in this class
        private final BottomNavigationMenuView mMenuView;

        2. get field in mButtons
        private BottomNavigationItemView[] mButtons;

        3. chang mShiftAmount to 0 in mButtons
        private final int mShiftAmount

        change mScaleUpFactor and mScaleDownFactor to 1f in mButtons
        private final float mScaleUpFactor
        private final float mScaleDownFactor

        4. change label font size in mButtons
        private final TextView mLargeLabel
        private final TextView mSmallLabel
         */

        // 1. get mMenuView
        val mMenuView = bottomNavigationMenuView
        // 2. get mButtons
        val mButtons = bottomNavigationItemViews
        // 3. change field mShiftingMode value in mButtons
        for (button in mButtons!!) {
            val mLargeLabel = getField<TextView>(button.javaClass, button, "largeLabel")
            val mSmallLabel = getField<TextView>(button.javaClass, button, "smallLabel")

            // if disable animation, need animationRecord the source value
            if (!enable) {
                if (!animationRecord) {
                    animationRecord = true
                    mShiftAmount = getField<Float>(button.javaClass, button, "shiftAmount")!!
                    mScaleUpFactor = getField<Float>(button.javaClass, button, "scaleUpFactor")!!
                    mScaleDownFactor = getField<Float>(button.javaClass, button, "scaleDownFactor")!!

                    mLargeLabelSize = mLargeLabel!!.textSize
                    mSmallLabelSize = mSmallLabel!!.textSize

                    //                    System.out.println("mShiftAmount:" + mShiftAmount + " mScaleUpFactor:"
                    //                            + mScaleUpFactor + " mScaleDownFactor:" + mScaleDownFactor
                    //                            + " mLargeLabel:" + mLargeLabelSize + " mSmallLabel:" + mSmallLabelSize);
                }
                // disable
                setField(button.javaClass, button, "shiftAmount", 0)
                setField(button.javaClass, button, "scaleUpFactor", 1)
                setField(button.javaClass, button, "scaleDownFactor", 1)

                // let the mLargeLabel font size equal to mSmallLabel
                mLargeLabel!!.setTextSize(TypedValue.COMPLEX_UNIT_PX, mSmallLabelSize)

                // debug start
                //                mLargeLabelSize = mLargeLabel.getTextSize();
                //                System.out.println("mLargeLabel:" + mLargeLabelSize);
                // debug end

            } else {
                // haven't change the value. It means it was the first call this method. So nothing need to do.
                if (!animationRecord)
                    return this
                // enable animation
                setField(button.javaClass, button, "shiftAmount", mShiftAmount)
                setField(button.javaClass, button, "scaleUpFactor", mScaleUpFactor)
                setField(button.javaClass, button, "scaleDownFactor", mScaleDownFactor)
                // restore
                mLargeLabel!!.setTextSize(TypedValue.COMPLEX_UNIT_PX, mLargeLabelSize)
            }
        }
        mMenuView.updateMenuView()
        return this
    }

    /**
     * @Deprecated use [.setLabelVisibilityMode]
     * enable the shifting mode for navigation
     *
     * @param enable It will has a shift animation if true. Otherwise all videos are the same width.
     */
    @Deprecated("")
    open fun enableShiftingMode(enable: Boolean): BottomNavigationViewInner {
        /*
        1. get field in this class
        private final BottomNavigationMenuView mMenuView;

        2. change field mShiftingMode value in mMenuView
        private boolean mShiftingMode = true;
         */
        // 1. get mMenuView
        //        BottomNavigationMenuView mMenuView = getBottomNavigationMenuView();
        // 2. change field mShiftingMode value in mMenuView
        //        setField(mMenuView.getClass(), mMenuView, "isShifting", enable);
        //        mMenuView.updateMenuView();
        labelVisibilityMode =
            if (enable) LabelVisibilityMode.LABEL_VISIBILITY_SELECTED else LabelVisibilityMode.LABEL_VISIBILITY_LABELED
        return this
    }

    /**
     * @Deprecated use [.setItemHorizontalTranslationEnabled]
     * enable the shifting mode for each item
     *
     * @param enable It will has a shift animation for item if true. Otherwise the item text always be shown.
     */
    @Deprecated("")
    open fun enableItemShiftingMode(enable: Boolean): BottomNavigationViewInner {
        /*
        1. get field in this class
        private final BottomNavigationMenuView mMenuView;

        2. get field in this mMenuView
        private BottomNavigationItemView[] mButtons;

        3. change field mShiftingMode value in mButtons
        private boolean mShiftingMode = true;
         */
        // 1. get mMenuView
        //        BottomNavigationMenuView mMenuView = getBottomNavigationMenuView();
        // 2. get buttons
        //        BottomNavigationItemView[] mButtons = getBottomNavigationItemViews();
        // 3. change field mShiftingMode value in mButtons
        //        for (BottomNavigationItemView button : mButtons) {
        //            button.setShifting(enable);
        //        }
        //        mMenuView.updateMenuView();

        isItemHorizontalTranslationEnabled = enable

        return this
    }

    /**
     * get menu item position in menu
     *
     * @param item
     * @return position if success, -1 otherwise
     */
    open fun getMenuItemPosition(item: MenuItem): Int {
        // get item id
        val itemId = item.itemId
        // get meunu
        val menu = menu
        val size = menu.size()
        for (i in 0 until size) {
            if (menu.getItem(i).itemId == itemId) {
                return i
            }
        }
        return -1
    }

    /**
     * set the current checked item
     *
     * @param index start from 0.
     */
    open fun setCurrentItem(index: Int): BottomNavigationViewInner {
        selectedItemId = menu.getItem(index).itemId
        return this
    }

    override fun setOnNavigationItemSelectedListener(listener: OnNavigationItemSelectedListener?) {
        // if not set up with view pager, the same with father
        if (null == mMyOnNavigationItemSelectedListener) {
            super.setOnNavigationItemSelectedListener(listener)
            return
        }

        mMyOnNavigationItemSelectedListener!!.setOnNavigationItemSelectedListener(listener)
    }

    /**
     * The lib has a default icon tint color. You can call this method to clear it if no need.
     * It usually used when you set two image for item.
     * @return
     */
    open fun clearIconTintColor(): BottomNavigationViewInner {
        bottomNavigationMenuView.iconTintList = null
        return this
    }

    /**
     * get private mButton in mMenuView at position
     *
     * @param position
     * @return
     */
    open fun getBottomNavigationItemView(position: Int): BottomNavigationItemView {
        return bottomNavigationItemViews!![position]
    }

    /**
     * get icon at position
     *
     * @param position
     * @return
     */
    open fun getIconAt(position: Int): ImageView? {
        /*
         * 1 private final BottomNavigationMenuView mMenuView;
         * 2 private BottomNavigationItemView[] mButtons;
         * 3 private ImageView mIcon;
         */
        val mButtons = getBottomNavigationItemView(position)
        return getField<ImageView>(BottomNavigationItemView::class.java, mButtons, "icon")
    }

    /**
     * get small label at position
     * Each item has tow label, one is large, another is small.
     *
     * @param position
     * @return
     */
    open fun getSmallLabelAt(position: Int): TextView? {
        /*
         * 1 private final BottomNavigationMenuView mMenuView;
         * 2 private BottomNavigationItemView[] mButtons;
         * 3 private final TextView mSmallLabel;
         */
        val mButtons = getBottomNavigationItemView(position)
        return getField<TextView>(BottomNavigationItemView::class.java, mButtons, "smallLabel")
    }

    /**
     * get large label at position
     * Each item has tow label, one is large, another is small.
     *
     * @param position
     * @return
     */
    open fun getLargeLabelAt(position: Int): TextView? {
        /*
         * 1 private final BottomNavigationMenuView mMenuView;
         * 2 private BottomNavigationItemView[] mButtons;
         * 3 private final TextView mLargeLabel;
         */
        val mButtons = getBottomNavigationItemView(position)
        return getField<TextView>(BottomNavigationItemView::class.java, mButtons, "largeLabel")
    }

    /**
     * set all item small TextView size
     * Each item has tow label, one is large, another is small.
     * Small one will be shown when item state is normal
     * Large one will be shown when item checked.
     *
     * @param sp
     */
    open fun setSmallTextSize(sp: Float): BottomNavigationViewInner {
        val count = itemCount
        for (i in 0 until count) {
            getSmallLabelAt(i)!!.textSize = sp
        }
        mMenuView!!.updateMenuView()
        return this
    }

    /**
     * set all item large TextView size
     * Each item has tow label, one is large, another is small.
     * Small one will be shown when item state is normal.
     * Large one will be shown when item checked.
     *
     * @param sp
     */
    open fun setLargeTextSize(sp: Float): BottomNavigationViewInner {
        val count = itemCount
        for (i in 0 until count) {
            val tvLarge = getLargeLabelAt(i)
            if (null != tvLarge)
                tvLarge.textSize = sp
        }
        mMenuView!!.updateMenuView()
        return this
    }

    /**
     * set all item large and small TextView size
     * Each item has tow label, one is large, another is small.
     * Small one will be shown when item state is normal
     * Large one will be shown when item checked.
     *
     * @param sp
     */
    open fun setTextSize(sp: Float): BottomNavigationViewInner {
        setLargeTextSize(sp)
        setSmallTextSize(sp)
        return this
    }

    /**
     * set item ImageView size which at position
     *
     * @param position position start from 0
     * @param width    in dp
     * @param height   in dp
     */
    open fun setIconSizeAt(position: Int, width: Float, height: Float): BottomNavigationViewInner {
        val icon = getIconAt(position)
        // update size
        val layoutParams = icon!!.layoutParams
        layoutParams.width =
            dp2px(
                context,
                width
            )
        layoutParams.height =
            dp2px(
                context,
                height
            )
        icon.layoutParams = layoutParams

        mMenuView!!.updateMenuView()
        return this
    }

    /**
     * set all item ImageView size
     *
     * @param width  in dp
     * @param height in dp
     */
    open fun setIconSize(width: Float, height: Float): BottomNavigationViewInner {
        val count = itemCount
        for (i in 0 until count) {
            setIconSizeAt(i, width, height)
        }
        return this
    }

    /**
     * set all item ImageView size
     *
     * @param dpSize  in dp
     */
    open fun setIconSize(dpSize: Float): BottomNavigationViewInner {
        itemIconSize = dp2px(
            context,
            dpSize
        )
        return this
    }

    /**
     * set menu item height
     *
     * @param height in px
     */
    open fun setItemHeight(height: Int): BottomNavigationViewInner {
        // 1. get mMenuView
        val mMenuView = bottomNavigationMenuView
        // 2. set private final int mItemHeight in mMenuView
        setField(mMenuView.javaClass, mMenuView, "itemHeight", height)

        mMenuView.updateMenuView()
        return this
    }

    /**
     * set Typeface for all item TextView
     *
     * @attr ref android.R.styleable#TextView_typeface
     * @attr ref android.R.styleable#TextView_textStyle
     */
    open fun setTypeface(typeface: Typeface, style: Int): BottomNavigationViewInner {
        val count = itemCount
        for (i in 0 until count) {
            getLargeLabelAt(i)!!.setTypeface(typeface, style)
            getSmallLabelAt(i)!!.setTypeface(typeface, style)
        }
        mMenuView!!.updateMenuView()
        return this
    }

    /**
     * set Typeface for all item TextView
     *
     * @attr ref android.R.styleable#TextView_typeface
     */
    open fun setTypeface(typeface: Typeface): BottomNavigationViewInner {
        val count = itemCount
        for (i in 0 until count) {
            getLargeLabelAt(i)!!.typeface = typeface
            getSmallLabelAt(i)!!.typeface = typeface
        }
        mMenuView!!.updateMenuView()
        return this
    }

    /**
     * get private filed in this specific object
     *
     * @param targetClass
     * @param instance    the filed owner
     * @param fieldName
     * @param <T>
     * @return field if success, null otherwise.
    </T> */
    @Suppress("UNCHECKED_CAST")
    private fun <T> getField(targetClass: Class<*>, instance: Any, fieldName: String): T? {
        try {
            val field = targetClass.getDeclaredField(fieldName)
            field.isAccessible = true
            return field.get(instance) as T
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }

        return null
    }

    /**
     * change the field value
     *
     * @param targetClass
     * @param instance    the filed owner
     * @param fieldName
     * @param value
     */
    private fun setField(targetClass: Class<*>, instance: Any, fieldName: String, value: Any) {
        try {
            val field = targetClass.getDeclaredField(fieldName)
            field.isAccessible = true
            field.set(instance, value)
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }

    }

    /**
     * This method will link the given ViewPager and this BottomNavigationViewInner together so that
     * changes in one are automatically reflected in the other. This includes scroll state changes
     * and clicks.
     *
     * @param viewPager
     */
    open fun setupWithViewPager(viewPager: ViewPager): BottomNavigationViewInner {
        return setupWithViewPager(viewPager, false)
    }

    /**
     * This method will link the given ViewPager and this BottomNavigationViewInner together so that
     * changes in one are automatically reflected in the other. This includes scroll state changes
     * and clicks.
     *
     * @param viewPager
     * @param smoothScroll whether ViewPager changed with smooth scroll animation
     */
    open fun setupWithViewPager(viewPager: ViewPager?, smoothScroll: Boolean): BottomNavigationViewInner {
        if (mViewPager != null) {
            // If we've already been setup with a ViewPager, remove us from it
            if (mPageChangeListener != null) {
                mViewPager!!.removeOnPageChangeListener(mPageChangeListener!!)
            }
        }

        if (null == viewPager) {
            mViewPager = null
            super.setOnNavigationItemSelectedListener(null)
            return this
        }

        mViewPager = viewPager

        // Add our custom OnPageChangeListener to the ViewPager
        if (mPageChangeListener == null) {
            mPageChangeListener =
                BottomNavigationViewExOnPageChangeListener(
                    this
                )
        }
        viewPager.addOnPageChangeListener(mPageChangeListener!!)

        // Now we'll add a navigation item selected listener to set ViewPager's current item
        val listener = onNavigationItemSelectedListener
        mMyOnNavigationItemSelectedListener =
            MyOnNavigationItemSelectedListener(
                viewPager,
                this,
                smoothScroll,
                listener
            )
        super.setOnNavigationItemSelectedListener(mMyOnNavigationItemSelectedListener)
        return this
    }

    /**
     * A [ViewPager.OnPageChangeListener] class which contains the
     * necessary calls back to the provided [BottomNavigationViewInner] so that the tab position is
     * kept in sync.
     *
     *
     *
     * This class stores the provided BottomNavigationViewInner weakly, meaning that you can use
     * [ addOnPageChangeListener(OnPageChangeListener)][ViewPager.addOnPageChangeListener] without removing the listener and
     * not cause a leak.
     */
    private class BottomNavigationViewExOnPageChangeListener(bnve: BottomNavigationViewInner) :
        ViewPager.OnPageChangeListener {
        private val mBnveRef: WeakReference<BottomNavigationViewInner>

        init {
            mBnveRef = WeakReference(bnve)
        }

        override fun onPageScrollStateChanged(state: Int) {}

        override fun onPageScrolled(
            position: Int, positionOffset: Float,
            positionOffsetPixels: Int
        ) {
        }

        override fun onPageSelected(position: Int) {
            val bnve = mBnveRef.get()
            if (null != bnve && !isNavigationItemClicking)
                bnve.setCurrentItem(position)
            //            Log.d("onPageSelected", "--------- position " + position + " ------------");
        }
    }

    /**
     * Decorate OnNavigationItemSelectedListener for setupWithViewPager
     */
    private class MyOnNavigationItemSelectedListener internal constructor(
        viewPager: ViewPager,
        bnve: BottomNavigationViewInner,
        private val smoothScroll: Boolean,
        private var listener: OnNavigationItemSelectedListener?
    ) : OnNavigationItemSelectedListener {
        private val viewPagerRef: WeakReference<ViewPager>
        private val items: SparseIntArray// used for change ViewPager selected item
        private var previousPosition = -1


        init {
            this.viewPagerRef = WeakReference(viewPager)

            // create videos
            val menu = bnve.menu
            val size = menu.size()
            items = SparseIntArray(size)
            for (i in 0 until size) {
                val itemId = menu.getItem(i).itemId
                items.put(itemId, i)
            }
        }

        fun setOnNavigationItemSelectedListener(listener: OnNavigationItemSelectedListener?) {
            this.listener = listener
        }

        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            val position = items.get(item.itemId)
            // only set item when item changed
            if (previousPosition == position) {
                return true
            }
            //            Log.d("onNavigationItemSelecte", "position:"  + position);
            // user listener
            if (null != listener) {
                val bool = listener!!.onNavigationItemSelected(item)
                // if the selected is invalid, no need change the view pager
                if (!bool)
                    return false
            }

            // change view pager
            val viewPager = viewPagerRef.get() ?: return false

            // use isNavigationItemClicking flag to avoid `ViewPager.OnPageChangeListener` trigger
            isNavigationItemClicking = true
            viewPager.setCurrentItem(items.get(item.itemId), smoothScroll)
            isNavigationItemClicking = false

            // update previous position
            previousPosition = position

            return true
        }

    }

    open fun enableShiftingMode(position: Int, enable: Boolean): BottomNavigationViewInner {
        getBottomNavigationItemView(position).setShifting(enable)
        return this
    }

    open fun setItemBackground(position: Int, background: Int): BottomNavigationViewInner {
        getBottomNavigationItemView(position).setItemBackground(background)
        return this
    }

    open fun setIconTintList(position: Int, tint: ColorStateList): BottomNavigationViewInner {
        getBottomNavigationItemView(position).setIconTintList(tint)
        return this
    }

    open fun setTextTintList(position: Int, tint: ColorStateList): BottomNavigationViewInner {
        getBottomNavigationItemView(position).setTextColor(tint)
        return this
    }

    /**
     * set margin top for all icons
     *
     * @param marginTop in px
     */
    open fun setIconsMarginTop(marginTop: Int): BottomNavigationViewInner {
        for (i in 0 until itemCount) {
            setIconMarginTop(i, marginTop)
        }
        return this
    }

    /**
     * set margin top for icon
     *
     * @param position
     * @param marginTop in px
     */
    open fun setIconMarginTop(position: Int, marginTop: Int): BottomNavigationViewInner {
        /*
        1. BottomNavigationItemView
        2. private final int mDefaultMargin;
         */
        val itemView = getBottomNavigationItemView(position)
        setField(BottomNavigationItemView::class.java, itemView, "defaultMargin", marginTop)
        mMenuView!!.updateMenuView()
        return this
    }

    companion object {
        // used for setupWithViewPager end

        // detect navigation tab changes when the user clicking on navigation item
        private var isNavigationItemClicking = false

        /**
         * get text height by font size
         *
         * @param fontSize
         * @return
         */
        private fun getFontHeight(fontSize: Float): Int {
            val paint = Paint()
            paint.textSize = fontSize
            val fm = paint.fontMetrics
            return Math.ceil((fm.descent - fm.top).toDouble()).toInt() + 2
        }

        /**
         * dp to px
         *
         * @param context
         * @param dpValue dp
         * @return px
         */
        fun dp2px(context: Context, dpValue: Float): Int {
            val scale = context.resources.displayMetrics.density
            return (dpValue * scale + 0.5f).toInt()
        }
    }

}
