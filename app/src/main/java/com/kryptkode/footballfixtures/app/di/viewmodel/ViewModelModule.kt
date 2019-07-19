package com.kryptkode.footballfixtures.app.di.viewmodel

import androidx.lifecycle.ViewModel
import com.kryptkode.footballfixtures.MainViewModel
import com.kryptkode.footballfixtures.competitions.CompetitionViewModel
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

}