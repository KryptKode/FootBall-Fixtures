package com.kryptkode.footballfixtures.app.di.local

import com.kryptkode.footballfixtures.app.data.db.AppDatabase
import com.kryptkode.footballfixtures.app.data.db.DbManager
import com.kryptkode.footballfixtures.app.di.app.scopes.AppScope
import com.kryptkode.footballfixtures.app.utils.schedulers.AppSchedulers
import dagger.Module
import dagger.Provides
import io.mockk.mockk

@Module
class TestDbModule {

    @Provides
    @AppScope
    fun provideDbManager(): DbManager {
        return mockk()
    }
}