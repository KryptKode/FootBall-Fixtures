package com.kryptkode.footballfixtures.competitions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.kryptkode.footballfixtures.app.base.viewmodel.BaseRepoViewModel
import com.kryptkode.footballfixtures.app.data.models.competition.Competition
import com.kryptkode.footballfixtures.app.data.repo.Repository
import com.kryptkode.footballfixtures.app.utils.SingleLiveEvent
import javax.inject.Inject

class CompetitionViewModel @Inject constructor(
    repository: Repository
) : BaseRepoViewModel(repository) {

    private val blank = MutableLiveData<Unit>()
    private var loaded = false

    private val _openDetail = SingleLiveEvent<Competition>()
    val openDetail: LiveData<Competition> = _openDetail

    private val repoResult = Transformations.map(blank) {
        repository.getCompetitions()
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

    fun loadData() {
        loadIfNotLoaded()
    }

    private fun loadIfNotLoaded() {
        if (!loaded) {
            blank.value = null
            loaded = true
        }
    }

    fun handleItemClick(competition: Competition?) {
        _openDetail.value = competition
    }
}