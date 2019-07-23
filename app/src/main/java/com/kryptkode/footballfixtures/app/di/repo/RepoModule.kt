package com.kryptkode.footballfixtures.app.di.repo

import com.kryptkode.footballfixtures.app.data.api.ApiManager
import com.kryptkode.footballfixtures.app.data.db.DbManager
import com.kryptkode.footballfixtures.app.data.repo.AppRepository
import com.kryptkode.footballfixtures.app.data.repo.Repository
import com.kryptkode.footballfixtures.app.di.app.scopes.AppScope
import com.kryptkode.footballfixtures.app.utils.ErrorHandler
import com.kryptkode.footballfixtures.app.utils.schedulers.AppSchedulers
import dagger.Module
import dagger.Provides

@Module
class RepoModule {


    @Provides
    @AppScope
    fun provideRepository(
        schedulers: AppSchedulers,
        dbManager: DbManager,
        apiManager: ApiManager,
        errorHandler: ErrorHandler
    ): Repository {
        return AppRepository(schedulers, dbManager, apiManager, errorHandler)
    }
}