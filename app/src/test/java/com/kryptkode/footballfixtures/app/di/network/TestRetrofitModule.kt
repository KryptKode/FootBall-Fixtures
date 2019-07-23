package com.kryptkode.footballfixtures.app.di.network

import com.kryptkode.footballfixtures.app.di.app.scopes.AppScope
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module(includes = [OkhttpClientModule::class, NetworkFactoriesModule::class])
class TestRetrofitModule {

    @Provides
    @AppScope
    fun provideRetrofit(
        client: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
        rxJavaCallAdapterFactory: RxJava2CallAdapterFactory,
        mockWebServer: MockWebServer
    ): Retrofit {
        val builder = Retrofit.Builder()
        builder.client(client)
        builder.addCallAdapterFactory(rxJavaCallAdapterFactory)
        builder.addConverterFactory(gsonConverterFactory)
        builder.baseUrl(mockWebServer.url("/"))
        return builder.build()
    }

}