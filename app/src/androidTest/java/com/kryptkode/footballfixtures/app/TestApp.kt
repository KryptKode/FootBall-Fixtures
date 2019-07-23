package com.kryptkode.footballfixtures.app

import com.kryptkode.footballfixtures.app.di.DaggerTestComponent
import com.kryptkode.footballfixtures.app.di.app.AppComponent

class TestApp : App(){

    override val appComponent: AppComponent
        get() = DaggerTestComponent
            .builder()
            .bindApplication(this)
            .build()
}