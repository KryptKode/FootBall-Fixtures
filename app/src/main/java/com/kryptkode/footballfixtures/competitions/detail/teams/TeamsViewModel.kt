package com.kryptkode.footballfixtures.competitions.detail.teams

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.kryptkode.footballfixtures.app.base.viewmodel.BaseRepoViewModel
import com.kryptkode.footballfixtures.app.data.models.team.Team
import com.kryptkode.footballfixtures.app.data.repo.Repository
import com.kryptkode.footballfixtures.app.utils.SingleLiveEvent
import timber.log.Timber
import javax.inject.Inject

class TeamsViewModel @Inject constructor(
    repository: Repository
) : BaseRepoViewModel(repository) {

    private val blank = MutableLiveData<Int>()
    private var loaded = false

    private val _openDetail = SingleLiveEvent<Team>()
    val openDetail: LiveData<Team> = _openDetail

    private val repoResult = Transformations.map(blank) {
        repository.getTeamsForCompetition(blank.value)
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

    fun handleItemClick(team: Team?) {
        _openDetail.value = team
    }

    fun loadData(competitionId: Int?) {
        Timber.d("Competition loading... $competitionId")
        loadIfNotLoaded(competitionId)
    }

    private fun loadIfNotLoaded(competitionId: Int?) {
        if (!loaded) {
            blank.value = competitionId
            loaded = true
        }
    }
}