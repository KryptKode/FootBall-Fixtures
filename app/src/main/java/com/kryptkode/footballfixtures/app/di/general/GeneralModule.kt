package com.kryptkode.footballfixtures.app.di.general

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kryptkode.footballfixtures.app.di.app.scopes.AppScope
import com.kryptkode.footballfixtures.app.utils.dispatchers.AppCoroutineDispatchers
import com.kryptkode.footballfixtures.app.utils.schedulers.AppSchedulers
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers


@Module
class GeneralModule {


    @Provides
    @AppScope
    fun provideGson(): Gson {
        val builder = GsonBuilder()
        return builder.create()
    }

    @Provides
    @AppScope
    fun provideDispatchers(): AppCoroutineDispatchers {
        return AppCoroutineDispatchers(
            network = Dispatchers.Default,
            io = Dispatchers.IO,
            main = Dispatchers.Main
        )
    }


    @Provides
    @AppScope
    fun provideSchedulers(): AppSchedulers {
        return AppSchedulers(
            network = Schedulers.computation(),
            io = Schedulers.io(),
            main = AndroidSchedulers.mainThread()
        )
    }


}