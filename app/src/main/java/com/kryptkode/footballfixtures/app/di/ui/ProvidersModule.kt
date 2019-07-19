package com.kryptkode.footballfixtures.app.di.ui

import com.kryptkode.footballfixtures.app.navigator.Fragments
import com.kryptkode.footballfixtures.competitions.CompetitionsFragment
import com.kryptkode.footballfixtures.todaysfixtures.TodaysFixturesFragment
import dagger.Module
import dagger.Provides

@Module
class ProvidersModule {

    @Provides
    fun provideFragments(
        todaysFixturesFragment: TodaysFixturesFragment,
        competitionsFragment: CompetitionsFragment
    ): Fragments {
        return Fragments(
            competitionsFragment,
            todaysFixturesFragment
        )
    }
}