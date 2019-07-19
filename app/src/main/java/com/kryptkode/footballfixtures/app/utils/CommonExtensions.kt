package com.kryptkode.footballfixtures.app.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.ViewPropertyAnimator


inline fun ViewPropertyAnimator.onAnimationEnd(crossinline continuation: (Animator) -> Unit): ViewPropertyAnimator {
    setListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator) {
            continuation(animation)
        }
    })

    return this
}