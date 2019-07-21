package com.kryptkode.footballfixtures.app.di

import com.kryptkode.footballfixtures.app.base.BaseTest
import com.kryptkode.footballfixtures.app.di.local.DbModule
import com.kryptkode.footballfixtures.app.di.network.RetrofitModule
import com.kryptkode.footballfixtures.app.di.viewmodel.ViewModelFactoryModule
import com.kryptkode.footballfixtures.app.di.viewmodel.ViewModelModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ViewModelModule::class, TestGeneralModule::class,
         ViewModelFactoryModule::class, RetrofitModule::class, DbModule::class]
)
interface TestAppComponent {
    fun inject(baseTest: BaseTest)
}