package com.kryptkode.footballfixtures.app.di.ui

import android.content.Context
import androidx.fragment.app.Fragment
import com.kryptkode.footballfixtures.R
import com.kryptkode.footballfixtures.app.navigator.Fragments
import com.kryptkode.footballfixtures.app.utils.Constants
import com.kryptkode.footballfixtures.competitions.CompetitionsFragment
import com.kryptkode.footballfixtures.competitions.detail.FragmentTitleProvider
import com.kryptkode.footballfixtures.competitions.detail.FragmentsListProvider
import com.kryptkode.footballfixtures.competitions.detail.fixtures.FixturesFragment
import com.kryptkode.footballfixtures.competitions.detail.table.TableFragment
import com.kryptkode.footballfixtures.competitions.detail.teams.TeamsFragment
import com.kryptkode.footballfixtures.todaysfixtures.TodaysFixturesFragment
import dagger.Module
import dagger.Provides
import javax.inject.Named

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

    @Provides
    fun listOfFragments(
        fixturesFragment: FixturesFragment,
        tableFragment: TableFragment,
        teamsFragment: TeamsFragment
    ): FragmentsListProvider {
        return FragmentsListProvider(listOf(tableFragment, fixturesFragment, teamsFragment))
    }

    @Provides
    fun listOfTitles(
        context: Context
    ): FragmentTitleProvider {
        return FragmentTitleProvider(
            listOf(
                context.getString(R.string.title_table),
                context.getString(R.string.title_fixtures),
                context.getString(R.string.title_teams)
            )
        )
    }
}