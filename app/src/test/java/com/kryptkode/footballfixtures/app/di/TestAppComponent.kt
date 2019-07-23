package com.kryptkode.footballfixtures.app.di

import com.kryptkode.footballfixtures.app.data.api.ApiManagerTest
import com.kryptkode.footballfixtures.app.data.callbacks.base.BaseBoundaryCallbackTest
import com.kryptkode.footballfixtures.app.di.app.scopes.AppScope
import com.kryptkode.footballfixtures.app.di.general.TestGeneralModule
import com.kryptkode.footballfixtures.app.di.local.TestDbModule
import com.kryptkode.footballfixtures.app.di.network.TestRetrofitModule
import com.kryptkode.footballfixtures.app.di.viewmodel.ViewModelFactoryModule
import com.kryptkode.footballfixtures.app.di.viewmodel.ViewModelModule
import dagger.Component

@AppScope
@Component(
    modules = [
        ViewModelModule::class, TestGeneralModule::class,
        ViewModelFactoryModule::class, TestRetrofitModule::class, TestDbModule::class]
)
interface TestAppComponent {
    fun inject(baseTest: BaseBoundaryCallbackTest)
    fun inject(apiManagerTest: ApiManagerTest)
}