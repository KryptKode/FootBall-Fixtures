package com.kryptkode.footballfixtures.app.data.callbacks

import android.content.Context
import com.kryptkode.footballfixtures.app.data.api.ApiManager
import com.kryptkode.footballfixtures.app.data.callbacks.base.BaseBoundaryCallback
import com.kryptkode.footballfixtures.app.data.db.DbManager
import com.kryptkode.footballfixtures.app.data.models.squad.Squad
import com.kryptkode.footballfixtures.app.data.models.team.Team
import com.kryptkode.footballfixtures.app.utils.ErrorHandler
import com.kryptkode.footballfixtures.app.utils.NetworkState
import com.kryptkode.footballfixtures.app.utils.schedulers.AppSchedulers
import com.kryptkode.footballfixtures.competitions.detail.teams.detail.adapter.TeamDetailAdapter
import timber.log.Timber

class SquadBoundaryCallback(
    private val schedulers: AppSchedulers,
    private val apiManager: ApiManager,
    private val dbManager: DbManager,
    private val context: Context,
    private val team: Team?

) : BaseBoundaryCallback<Squad>() {
    override fun requestAndSaveData() {
        if (networkState.value == NetworkState.LOADING) return
        networkState.postValue(NetworkState.LOADING)
        val teamId = team?.id
        disposable.add(apiManager.getSquadForTeam(teamId)
            .map {

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
                val error = ErrorHandler(context).getErrorMessage(it)
                networkState.postValue(NetworkState.error(error))
            }, {
                networkState.postValue(NetworkState.LOADED)
            })
        )
    }
}