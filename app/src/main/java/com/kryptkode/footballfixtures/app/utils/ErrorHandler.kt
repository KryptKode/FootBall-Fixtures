package com.kryptkode.footballfixtures.app.utils

import android.content.Context
import com.kryptkode.footballfixtures.R
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class ErrorHandler @Inject constructor(var context: Context) {


    fun getErrorMessage(e: Throwable): String {
        if (e is ConnectException){
            return context.getString(R.string.connect_exception)
        }
        if (e is UnknownHostException){
            return context.getString(R.string.unknown_host_exception)
        }
        if (e is SocketTimeoutException){
            return context.getString(R.string.timed_out_exception)
        }

        return context.getString(R.string.unknown_exception)
    }
}