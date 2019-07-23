package com.kryptkode.footballfixtures.app.data.callbacks

import androidx.annotation.VisibleForTesting
import com.kryptkode.footballfixtures.app.data.api.ApiManager
import com.kryptkode.footballfixtures.app.data.api.models.SquadResponse
import com.kryptkode.footballfixtures.app.data.callbacks.base.BaseBoundaryCallback
import com.kryptkode.footballfixtures.app.data.db.DbManager
import com.kryptkode.footballfixtures.app.data.models.squad.Squad
import com.kryptkode.footballfixtures.app.data.models.team.Team
import com.kryptkode.footballfixtures.app.utils.ErrorHandler
import com.kryptkode.footballfixtures.app.utils.NetworkState
import com.kryptkode.footballfixtures.app.utils.schedulers.AppSchedulers
import timber.log.Timber

class SquadBoundaryCallback(
    private val teamId:Int?,
    schedulers: AppSchedulers,
    apiManager: ApiManager,
    dbManager: DbManager,
    errorHandler: ErrorHandler

) : BaseBoundaryCallback<Squad>(schedulers, apiManager, dbManager, errorHandler) {

    @VisibleForTesting
    var squadResponse : SquadResponse? = null

    override fun requestAndSaveData() {
        if (networkState.value == NetworkState.LOADING) return
        networkState.postValue(NetworkState.LOADING)
        val teamId = teamId
        disposable.add(apiManager.getSquadForTeam(teamId)
            .map {
                squadResponse = it
                val teamList = it.squad ?: mutableListOf()
                for (i in teamList) {
                    i.teamId = teamId
                }
                teamList
            }
            .flatMap { dbManager.insertSquad(it) }
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