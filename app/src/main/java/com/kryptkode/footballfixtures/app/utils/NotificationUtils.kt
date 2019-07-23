package com.kryptkode.footballfixtures.app.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat

import com.google.android.material.snackbar.Snackbar
import com.kryptkode.footballfixtures.R


class NotificationUtils {

    companion object {
        const val CHANNEL_ONE_ID = "com.blog.api.ONE"
        @RequiresApi(api = Build.VERSION_CODES.O)
        fun createChannelIfNotExist(notificationManager: NotificationManager?, title: String) {
            if (notificationManager?.getNotificationChannel(CHANNEL_ONE_ID) == null) {
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val notificationChannel = NotificationChannel(
                    CHANNEL_ONE_ID,
                    title, importance
                )
                notificationChannel.enableLights(true)
                notificationChannel.lightColor = Color.BLUE
                notificationChannel.setShowBadge(true)
                notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
                notificationManager?.createNotificationChannel(notificationChannel)
            }
        }


        @JvmStatic
        fun notifyUser(context: Context, message: String) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        @JvmStatic
        fun notifyUser(view: View, message: String) {
            val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
            val snackBarLayout = snackbar.view as Snackbar.SnackbarLayout
            snackBarLayout.setBackgroundColor(
                ContextCompat.getColor(
                    view.context,
                    AttrUtils.convertAttrToColor(R.attr.colorSecondary, view.context)
                )
            )
            (snackBarLayout.findViewById<View>(R.id.snackbar_text) as TextView)
                .setTextColor(AttrUtils.convertAttrToColor(R.attr.colorOnSecondary, view.context))

            snackbar.show()

        }

    }
}