package com.kryptkode.footballfixtures.app.data.callbacks

import com.kryptkode.footballfixtures.app.data.api.ApiManager
import com.kryptkode.footballfixtures.app.data.api.models.TeamResponse
import com.kryptkode.footballfixtures.app.data.callbacks.base.BaseBoundaryCallback
import com.kryptkode.footballfixtures.app.data.db.DbManager
import com.kryptkode.footballfixtures.app.data.models.team.Team
import com.kryptkode.footballfixtures.app.utils.ErrorHandler
import com.kryptkode.footballfixtures.app.utils.NetworkState
import com.kryptkode.footballfixtures.app.utils.schedulers.AppSchedulers
import timber.log.Timber
import javax.inject.Inject

class TeamBoundaryCallback
@Inject constructor(
    private val competitionId: Int?,
    schedulers: AppSchedulers,
    apiManager: ApiManager,
    dbManager: DbManager,
    errorHandler: ErrorHandler
) : BaseBoundaryCallback<Team>(schedulers, apiManager, dbManager, errorHandler) {
    var teamResponse : TeamResponse? = null
    override fun requestAndSaveData() {
        if (networkState.value == NetworkState.LOADING) return
        networkState.postValue(NetworkState.LOADING)
        disposable.add(apiManager.getTeamForCompetition(competitionId)
            .map {
                teamResponse = it
                val teamList = it.teams ?: mutableListOf()
                for (i in teamList) {
                    i.competitionId = competitionId
                }
                teamList
            }
            .flatMap { dbManager.insertTeams(it) }
            .subscribeOn(schedulers.network).subscribe({
                networkState.postValue(NetworkState.LOADED)
            }, {
                Timber.e(it)
                val error = errorHandler.getErrorMessage(it)
                networkState.postValue(NetworkState.error(error))
            }, {
                networkState.postValue(NetworkState.LOADED)
            })
        )
    }
}