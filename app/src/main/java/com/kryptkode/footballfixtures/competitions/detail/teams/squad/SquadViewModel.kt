package com.kryptkode.footballfixtures.competitions.detail.teams.squad

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.kryptkode.footballfixtures.app.base.viewmodel.BaseViewModel
import com.kryptkode.footballfixtures.app.data.models.team.Team
import com.kryptkode.footballfixtures.app.data.repo.AppRepository
import com.kryptkode.footballfixtures.app.utils.SingleLiveEvent
import timber.log.Timber
import javax.inject.Inject

class SquadViewModel @Inject constructor(
    private val repository: AppRepository
) : BaseViewModel() {
    private val _close = SingleLiveEvent<Unit>()
    val close: LiveData<Unit> = _close

    private val blank = MutableLiveData<Int>()
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

    fun loadData(teamId: Int?) {
        Timber.d("Competition loading... $teamId")
        loadIfNotLoaded(teamId)
    }

    private fun loadIfNotLoaded(teamId: Int?) {
        if (!loaded) {
            blank.value = teamId
            loaded = true
        }
    }

    fun handleNavigationIconClick() {
        _close.value = Unit
    }

}