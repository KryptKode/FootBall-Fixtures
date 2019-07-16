package com.kryptkode.imageloader

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

class ImageLoader {
    private var targetView: ImageView? = null
    private var url: String? = null

    @DrawableRes
    private var placeHolderRes: Int? = null
    private var placeHolderDrawable: Drawable? = null

    @DrawableRes
    private var errorRes: Int? = null

    fun with(view: ImageView): ImageLoader {
        this.targetView = view
        return this
    }

    fun load(url: String?): ImageLoader {
        this.url = url
        return this
    }

    fun placeholder(@DrawableRes res: Int): ImageLoader {
        this.placeHolderRes = res
        return this
    }

    fun placeholder(drawable: Drawable): ImageLoader {
        this.placeHolderDrawable = drawable
        return this
    }

    fun error(@DrawableRes res: Int): ImageLoader {
        this.errorRes = res
        return this
    }

    fun begin() {
        GlideApp.with(targetView ?: return)
            .load(url)
            .placeholder(
                placeHolderDrawable ?: ContextCompat.getDrawable(
                    targetView?.context ?: return, placeHolderRes ?: return
                ) ?: return
            )
            .error(errorRes ?: return)
            .into(targetView ?: return)
    }
}