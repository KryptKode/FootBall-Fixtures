package com.kryptkode.footballfixtures.app.di.viewmodel

import androidx.lifecycle.ViewModelProvider
import com.kryptkode.footballfixtures.app.di.viewmodel.DaggerViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: DaggerViewModelFactory): ViewModelProvider.Factory
}