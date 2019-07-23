package com.kryptkode.footballfixtures.app.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.getSystemService
import androidx.lifecycle.*
import androidx.localbroadcastmanager.content.LocalBroadcastManager

import timber.log.Timber


class NetworkListener(private val context: Context) : LifecycleObserver {
    private val _networkSubject = MutableLiveData<Boolean>()
    val networkSubject: LiveData<Boolean> = _networkSubject
    private lateinit var receiver: BroadcastReceiver
    private lateinit var networkCallback: ConnectivityManager.NetworkCallback

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun start() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setupConnectionListener()
        } else {
            initializeReceiver()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stop() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val connectivityManager = context.applicationContext.getSystemService<ConnectivityManager>()
            connectivityManager?.unregisterNetworkCallback(networkCallback)
        } else {
            LocalBroadcastManager.getInstance(context).unregisterReceiver(receiver)
        }
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setupConnectionListener() {
        Timber.d("Setting up connectivity listener")
        networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onLost(network: Network?) {
                Timber.d(" lost connection")
                _networkSubject.postValue(false)
            }

            override fun onAvailable(network: Network?) {
                Timber.d(" gained connection")
                _networkSubject.postValue(true)
            }
        }
        val networkRequest = NetworkRequest.Builder().build()
        val connectivityManager = context.applicationContext.getSystemService<ConnectivityManager>()
        connectivityManager?.registerNetworkCallback(networkRequest, networkCallback)
    }


    @Suppress("DEPRECATION")
    private fun initializeReceiver() {
        receiver = ConnectivityReceiver(this)
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        LocalBroadcastManager.getInstance(context).registerReceiver(receiver, filter)
    }


    @Suppress("DEPRECATION")
    class ConnectivityReceiver(private val listener: NetworkListener) : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (context != null) {

                val stateChange = NetworkUtils.isOffline(context)
                Timber.d("Network state changed to: %s", stateChange)
                listener._networkSubject.postValue(stateChange)
            }
        }


    }

}