package com.kryptkode.imageloader

import android.graphics.drawable.Drawable
import android.graphics.drawable.PictureDrawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.kryptkode.imageloader.svg.SvgSoftwareLayerSetter

class ImageLoader {
    private var targetView: ImageView? = null
    private var url: String? = null

    @DrawableRes
    private var placeHolderRes: Int? = null
    private var placeHolderDrawable: Drawable? = null

    @DrawableRes
    private var errorRes: Int? = null
    private var errorDrawable: Drawable? = null

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

    fun error(drawable: Drawable?): ImageLoader {
        this.errorDrawable = drawable
        return this
    }

    fun begin() {
        GlideApp.with(targetView ?: return)
            .`as`(PictureDrawable::class.java)
            .load(url)
            .listener(SvgSoftwareLayerSetter())
            .placeholder(
                placeHolderDrawable ?: ContextCompat.getDrawable(
                    targetView?.context ?: return, placeHolderRes ?: return
                ) ?: return
            )
            .error(
                errorDrawable ?: ContextCompat.getDrawable(
                    targetView?.context ?: return, errorRes ?: return
                ) ?: return
            )
            .into(targetView ?: return)
    }
}