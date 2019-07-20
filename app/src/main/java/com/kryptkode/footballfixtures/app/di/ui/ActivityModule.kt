package com.kryptkode.footballfixtures.app.di.ui


import com.kryptkode.footballfixtures.MainActivity
import com.kryptkode.footballfixtures.competitions.detail.CompetitionsDetailActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [FragmentModule::class, ProvidersModule::class])
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun bindCompetitionsDetailActivity(): CompetitionsDetailActivity

}