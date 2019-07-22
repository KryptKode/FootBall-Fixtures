package com.kryptkode.footballfixtures.app.di.viewmodel

import androidx.lifecycle.ViewModel
import com.kryptkode.footballfixtures.MainViewModel
import com.kryptkode.footballfixtures.competitions.CompetitionViewModel
import com.kryptkode.footballfixtures.competitions.detail.CompetitionsDetailViewModel
import com.kryptkode.footballfixtures.competitions.detail.fixtures.FixturesViewModel
import com.kryptkode.footballfixtures.competitions.detail.table.TableViewModel
import com.kryptkode.footballfixtures.competitions.detail.teams.TeamsViewModel
import com.kryptkode.footballfixtures.competitions.detail.teams.squad.SquadViewModel
import com.kryptkode.footballfixtures.todaysfixtures.TodaysFixturesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainActivityViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CompetitionViewModel::class)
    abstract fun bindCompetitionViewModel(viewModel: CompetitionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TodaysFixturesViewModel::class)
    abstract fun bindTodaysFixturesViewModel(viewModel: TodaysFixturesViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(FixturesViewModel::class)
    abstract fun bindFixturesViewModel(viewModel: FixturesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TableViewModel::class)
    abstract fun bindTableViewModel(viewModel: TableViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TeamsViewModel::class)
    abstract fun bindTeamsViewModel(viewModel: TeamsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CompetitionsDetailViewModel::class)
    abstract fun bindCompetitionsDetailViewModel(viewModel: CompetitionsDetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SquadViewModel::class)
    abstract fun bindTeamDetailViewModel(viewModel: SquadViewModel): ViewModel


}