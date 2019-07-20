package com.kryptkode.footballfixtures.competitions.detail.teams.detail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.kryptkode.footballfixtures.app.base.viewmodel.BaseViewModel
import com.kryptkode.footballfixtures.app.data.models.competition.Competition
import com.kryptkode.footballfixtures.app.data.models.team.Team
import com.kryptkode.footballfixtures.app.data.repo.AppRepository
import com.kryptkode.footballfixtures.app.utils.SingleLiveEvent
import timber.log.Timber
import javax.inject.Inject

class TeamDetailViewModel @Inject constructor(
    private val repository: AppRepository,
    application: Application
) : BaseViewModel(application) {
    private val _close = SingleLiveEvent<Unit>()
    val close: LiveData<Unit> = _close

    private val blank = MutableLiveData<Team>()
    private var loaded = false

    private val repoResult = Transformations.map(blank) {
        repository.getSquadForTeam(blank.value)
    }

    val networkState = Transformations.switchMap(repoResult) {
        it.refreshState
    }

    val repoList = Transformations.switchMap(repoResult) {
        it.data
    }


    val listEmpty = Transformations.map(repoList) {
        it.isEmpty()
    }

    fun refresh() {
        val repos = repoResult.value
        repos?.refresh?.invoke()
    }

    fun loadData(team: Team?) {
        Timber.d("Competition loading... ${team}")
        loadIfNotLoaded(team)
    }

    private fun loadIfNotLoaded(team: Team?) {
        if (!loaded) {
            blank.value = team
            loaded = true
        }
    }

    fun handleNavigationIconClick() {
        _close.value = Unit
    }

}