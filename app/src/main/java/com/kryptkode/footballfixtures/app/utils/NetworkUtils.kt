package com.kryptkode.footballfixtures.app.utils

import android.content.Context
import android.net.ConnectivityManager

object NetworkUtils {

    @Suppress("DEPRECATION")
    fun isOffline(
        context: Context
    ): Boolean {
        val manager = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return manager.activeNetworkInfo?.isConnectedOrConnecting != true
    }
}
