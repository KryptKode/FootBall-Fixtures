package com.kryptkode.footballfixtures.app.di.network

import android.content.Context
import com.kryptkode.footballfixtures.app.di.app.scopes.AppScope
import com.kryptkode.footballfixtures.app.di.network.InterceptorsModule
import com.kryptkode.footballfixtures.app.utils.Constants
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

import java.io.File
import java.util.concurrent.TimeUnit

@Module(includes = [InterceptorsModule::class])
class OkhttpClientModule {

    @AppScope
    @Provides
    fun provideFile(context: Context): File{
        return File(context.cacheDir, Constants.OK_HTTP_CACHE)
    }

    @AppScope
    @Provides
    fun provideCache(file: File): Cache?{
        return Cache(file, 10 * 1000 * 1000)
    }

    @AppScope
    @Provides
    fun provideOkhttpClient(cache: Cache?, httpInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(httpInterceptor)
        builder.cache(cache)
        builder.retryOnConnectionFailure(true)
        builder.writeTimeout(Constants.WRITE_TIME_OUT, TimeUnit.SECONDS)
        builder.connectTimeout(Constants.CONNECT_TIME_OUT, TimeUnit.SECONDS)

        return builder.build()
    }
}