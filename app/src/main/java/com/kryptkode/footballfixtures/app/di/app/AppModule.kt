package com.kryptkode.footballfixtures.app.di.app

import android.app.Application
import android.content.Context
import com.kryptkode.footballfixtures.app.App

import com.kryptkode.footballfixtures.app.di.app.scopes.AppScope
import com.kryptkode.footballfixtures.app.di.general.GeneralModule
import com.kryptkode.footballfixtures.app.di.local.DbModule
import com.kryptkode.footballfixtures.app.di.network.RetrofitModule
import com.kryptkode.footballfixtures.app.di.repo.RepoModule
import com.kryptkode.footballfixtures.app.di.ui.ActivityModule
import com.kryptkode.footballfixtures.app.di.viewmodel.ViewModelFactoryModule
import com.kryptkode.footballfixtures.app.di.viewmodel.ViewModelModule


import dagger.Binds
import dagger.Module

@Module(
    includes = [ViewModelModule::class, GeneralModule::class,
        ActivityModule::class, ViewModelFactoryModule::class, RetrofitModule::class, DbModule::class, RepoModule::class]
)
abstract class AppModule {

    @Binds
    @AppScope
    abstract fun provideApplicationContext(application: App): Context


    @Binds
    @AppScope
    abstract fun provideApplication(application: App): Application
}