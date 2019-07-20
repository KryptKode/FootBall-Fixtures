package com.kryptkode.footballfixtures.app.data.callbacks

import android.content.Context
import com.kryptkode.footballfixtures.app.data.api.ApiManager
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
    private val schedulers: AppSchedulers,
    private val apiManager: ApiManager,
    private val dbManager: DbManager,
    private val context: Context,
    protected val competitionId: Int?
) : BaseBoundaryCallback<Team>() {

    override fun requestAndSaveData() {
        if (networkState.value == NetworkState.LOADING) return
        networkState.postValue(NetworkState.LOADING)
        disposable.add(apiManager.getTeamForCompetition(competitionId)
            .map {

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
                val error = ErrorHandler(context).getErrorMessage(it)
                networkState.postValue(NetworkState.error(error))
            }, {
                networkState.postValue(NetworkState.LOADED)
            })
        )
    }
}