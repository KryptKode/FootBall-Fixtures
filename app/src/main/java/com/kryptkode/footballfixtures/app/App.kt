package com.kryptkode.footballfixtures.app

import android.content.Context
import androidx.multidex.MultiDex
import com.kryptkode.footballfixtures.BuildConfig
import com.kryptkode.footballfixtures.app.di.app.DaggerAppComponent
import dagger.android.support.DaggerApplication

import timber.log.Timber

open class App : DaggerApplication() {

    open val appComponent by lazy {
        DaggerAppComponent
            .builder()
            .bindApplication(this)
            .build()
    }


    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        initLogger()
    }

    override fun applicationInjector() = appComponent

    private fun initLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}