package com.kryptkode.footballfixtures.app.data.callbacks

import androidx.annotation.VisibleForTesting
import com.kryptkode.footballfixtures.app.data.api.ApiManager
import com.kryptkode.footballfixtures.app.data.api.models.FixturesResponse
import com.kryptkode.footballfixtures.app.data.callbacks.base.BaseBoundaryCallback
import com.kryptkode.footballfixtures.app.data.db.DbManager
import com.kryptkode.footballfixtures.app.data.models.fixtures.Match
import com.kryptkode.footballfixtures.app.utils.ErrorHandler
import com.kryptkode.footballfixtures.app.utils.NetworkState
import com.kryptkode.footballfixtures.app.utils.schedulers.AppSchedulers
import timber.log.Timber
import javax.inject.Inject

class FixturesBoundaryCallback
@Inject constructor(
    private val competitionId: Int?,
    schedulers: AppSchedulers,
    apiManager: ApiManager,
    dbManager: DbManager,
    errorHandler: ErrorHandler
) : BaseBoundaryCallback<Match>(schedulers, apiManager, dbManager, errorHandler) {

    @VisibleForTesting
    var fixturesResponse: FixturesResponse? = null

    override fun requestAndSaveData() {
        if (networkState.value == NetworkState.LOADING) return
        networkState.postValue(NetworkState.LOADING)
        disposable.add(apiManager.getFixturesForCompetition(competitionId)
            .map {
                fixturesResponse = it
                val teamList = it.matches
                for (i in teamList) {
                    i.competitionId = competitionId
                }
                teamList
            }
            .flatMap { dbManager.insertMatches(it) }
            .subscribeOn(schedulers.network).subscribe({
                networkState.postValue(NetworkState.LOADED)
            }, {
                Timber.e(it)
                val error = errorHandler.getErrorMessage(it)
                networkState.postValue(NetworkState.error(error))
            })
        )
    }
}