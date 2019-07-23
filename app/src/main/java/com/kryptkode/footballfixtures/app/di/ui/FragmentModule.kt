package com.kryptkode.footballfixtures.app.di.ui

import com.kryptkode.footballfixtures.competitions.CompetitionsFragment
import com.kryptkode.footballfixtures.competitions.detail.fixtures.FixturesFragment
import com.kryptkode.footballfixtures.competitions.detail.table.TableFragment
import com.kryptkode.footballfixtures.competitions.detail.teams.TeamsFragment
import com.kryptkode.footballfixtures.competitions.detail.teams.squad.SquadFragment
import com.kryptkode.footballfixtures.todaysfixtures.TodaysFixturesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun todaysFixturesFragment(): TodaysFixturesFragment

    @ContributesAndroidInjector
    abstract fun competitionsFragment(): CompetitionsFragment

    @ContributesAndroidInjector
    abstract fun fixturesFragment(): FixturesFragment

    @ContributesAndroidInjector
    abstract fun tableFragment(): TableFragment

    @ContributesAndroidInjector
    abstract fun teamFragment(): TeamsFragment

    @ContributesAndroidInjector
    abstract fun teamDetailFragment(): SquadFragment

}