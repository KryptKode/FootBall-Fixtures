package com.kryptkode.footballfixtures.app.di.app

import com.kryptkode.footballfixtures.app.di.app.scopes.AppScope
import com.kryptkode.footballfixtures.app.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerApplication

@AppScope
@Component(modules = [AndroidInjectionModule::class, AndroidSupportInjectionModule::class, AppModule::class])
interface AppComponent : AndroidInjector<DaggerApplication> {
    fun inject(application: App)

    @Component.Builder
    interface Builder{

        @BindsInstance
        fun bindApplication(application: App): Builder

        fun build(): AppComponent
    }
}