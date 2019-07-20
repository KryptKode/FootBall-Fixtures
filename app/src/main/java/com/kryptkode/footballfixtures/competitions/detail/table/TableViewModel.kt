package com.kryptkode.footballfixtures.competitions.detail.table

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.kryptkode.footballfixtures.app.base.viewmodel.BaseViewModel
import com.kryptkode.footballfixtures.app.data.repo.AppRepository
import timber.log.Timber
import javax.inject.Inject

class TableViewModel @Inject constructor(
    private val repository: AppRepository,
    application: Application
) : BaseViewModel(application) {

    private val blank = MutableLiveData<Int>()
    private var loaded = false

    private val repoResult = Transformations.map(blank) {
        repository.getTableForCompetition(blank.value)
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