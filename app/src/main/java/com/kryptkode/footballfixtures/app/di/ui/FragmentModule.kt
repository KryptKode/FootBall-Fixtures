package com.kryptkode.footballfixtures.app.di.ui

import com.kryptkode.footballfixtures.competitions.CompetitionsFragment
import com.kryptkode.footballfixtures.todaysfixtures.TodaysFixturesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun todaysFixturesFragment(): TodaysFixturesFragment

    @ContributesAndroidInjector
    abstract fun competitionsFragment(): CompetitionsFragment


}