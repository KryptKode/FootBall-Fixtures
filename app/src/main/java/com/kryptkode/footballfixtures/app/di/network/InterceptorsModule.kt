package com.kryptkode.footballfixtures.app.di.network



import com.kryptkode.footballfixtures.app.di.app.scopes.AppScope
import dagger.Module
import dagger.Provides
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber

@Module
class InterceptorsModule{

    @Provides
    @AppScope
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
            message -> Timber.e(message)
        }).setLevel(HttpLoggingInterceptor.Level.BODY)
    }
}