package com.kryptkode.footballfixtures.todaysfixtures

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.kryptkode.footballfixtures.app.base.viewmodel.BaseViewModel
import com.kryptkode.footballfixtures.app.data.repo.AppRepository
import javax.inject.Inject

class TodaysFixturesViewModel @Inject constructor(
    private val repository: AppRepository,
    application: Application) : BaseViewModel(application){

    private val blank = MutableLiveData<Unit>()
    private var loaded = false

    private val repoResult = Transformations.map(blank) {
        repository.getMatches()
    }

    val networkState = Transformations.switchMap(repoResult) {
        it.refreshState
    }

    val repoList = Transformations.switchMap(repoResult) {
        it.data
    }

    val listEmpty = Transformations.map(repoList){
        it.isEmpty()
    }

    fun refresh() {
        val repos = repoResult.value
        repos?.refresh?.invoke()
    }

    fun loadData(){
        loadIfNotLoaded()
    }

    private fun loadIfNotLoaded() {
        if (!loaded) {
            blank.value = null
            loaded = true
        }
    }
}