package com.kryptkode.footballfixtures.app.di.app

import android.app.Application
import android.content.Context
import com.kryptkode.footballfixtures.app.TestApp
import com.kryptkode.footballfixtures.app.di.app.scopes.AppScope
import com.kryptkode.footballfixtures.app.di.general.GeneralModule
import com.kryptkode.footballfixtures.app.di.local.DbModule
import com.kryptkode.footballfixtures.app.di.network.RetrofitModule
import com.kryptkode.footballfixtures.app.di.ui.ActivityModule
import com.kryptkode.footballfixtures.app.di.viewmodel.ViewModelFactoryModule
import com.kryptkode.footballfixtures.app.di.viewmodel.ViewModelModule
import dagger.Binds
import dagger.Module

@Module(includes = [ViewModelModule::class, GeneralModule::class,
    ActivityModule::class, ViewModelFactoryModule::class, RetrofitModule::class, DbModule::class])
abstract class TestAppModule{
    @Binds
    @AppScope
    abstract fun provideApplicationContext(application: TestApp): Context


    @Binds
    @AppScope
    abstract fun provideApplication(application: TestApp): Application
}