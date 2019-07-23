package com.kryptkode.footballfixtures.app.di.repo

import com.kryptkode.footballfixtures.app.data.mock.FakeDataSource
import com.kryptkode.footballfixtures.app.data.repo.Repository
import com.kryptkode.footballfixtures.app.data.repo.TestAppRepository
import com.kryptkode.footballfixtures.app.di.app.scopes.AppScope
import dagger.Module
import dagger.Provides

@Module
class TestRepoModule {

    @Provides
    @AppScope
    fun provideRepository(fakeDataSource: FakeDataSource): Repository {
        return TestAppRepository(fakeDataSource)
    }
}