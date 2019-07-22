package com.kryptkode.footballfixtures.app.di.general

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kryptkode.footballfixtures.app.di.app.scopes.AppScope
import com.kryptkode.footballfixtures.app.utils.dispatchers.AppCoroutineDispatchers
import com.kryptkode.footballfixtures.app.utils.schedulers.AppSchedulers
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import okhttp3.mockwebserver.MockWebServer
import javax.inject.Named
import javax.inject.Singleton

@Module
class TestGeneralModule {

    @Provides
    @AppScope
    fun provideSchedulers(): AppSchedulers {
        return AppSchedulers(
            network = Schedulers.trampoline(),
            io = Schedulers.trampoline(),
            main = Schedulers.trampoline()
        )
    }

    @Provides
    @AppScope
    fun provideGson(): Gson {
        val builder = GsonBuilder()
        return builder.create()
    }

    @Provides
    @AppScope
    fun provideMockWebServer():MockWebServer{
        return MockWebServer()
    }
}