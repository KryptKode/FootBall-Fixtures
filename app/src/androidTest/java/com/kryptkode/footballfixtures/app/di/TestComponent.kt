package com.kryptkode.footballfixtures.app.di

import com.kryptkode.footballfixtures.app.TestApp
import com.kryptkode.footballfixtures.app.di.app.AppComponent
import com.kryptkode.footballfixtures.app.di.app.TestAppModule
import com.kryptkode.footballfixtures.app.di.app.scopes.AppScope
import com.kryptkode.footballfixtures.app.di.repo.TestRepoModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule

@AppScope
@Component(modules = [AndroidInjectionModule::class, AndroidSupportInjectionModule::class, TestAppModule::class, TestRepoModule::class])
interface TestComponent : AppComponent {
    fun inject(testApp: TestApp)
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun bindApplication(application: TestApp): Builder

        fun build(): TestComponent
    }
}