package com.kryptkode.footballfixtures.app.di

import com.kryptkode.footballfixtures.app.di.app.scopes.AppScope
import com.kryptkode.footballfixtures.app.utils.dispatchers.AppCoroutineDispatchers
import com.kryptkode.footballfixtures.app.utils.schedulers.AppSchedulers
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import javax.inject.Named
import javax.inject.Singleton

@Module
class TestGeneralModule {

    @Provides
    @AppScope
    fun provideSchedulers(): AppSchedulers {
        return AppSchedulers(
            network = AndroidSchedulers.mainThread(),
            io = AndroidSchedulers.mainThread(),
            main = AndroidSchedulers.mainThread()
        )
    }
}