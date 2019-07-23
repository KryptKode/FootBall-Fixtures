package com.kryptkode.footballfixtures.app.data.callbacks

import androidx.annotation.VisibleForTesting
import com.kryptkode.footballfixtures.app.data.api.ApiManager
import com.kryptkode.footballfixtures.app.data.api.models.TodaysFixturesResponse
import com.kryptkode.footballfixtures.app.data.callbacks.base.BaseBoundaryCallback
import com.kryptkode.footballfixtures.app.data.db.DbManager
import com.kryptkode.footballfixtures.app.data.models.fixtures.Match
import com.kryptkode.footballfixtures.app.utils.ErrorHandler
import com.kryptkode.footballfixtures.app.utils.NetworkState
import com.kryptkode.footballfixtures.app.utils.schedulers.AppSchedulers
import timber.log.Timber

class MatchesBoundaryCallback(
    schedulers: AppSchedulers,
    apiManager: ApiManager,
    dbManager: DbManager,
    errorHandler: ErrorHandler
) : BaseBoundaryCallback<Match>(schedulers, apiManager, dbManager, errorHandler) {

    @VisibleForTesting
    var todaysFixturesResponse: TodaysFixturesResponse? = null

    override fun requestAndSaveData() {
        if (networkState.value == NetworkState.LOADING) return
        networkState.postValue(NetworkState.LOADING)
        disposable.add(apiManager.getTodaysFixtures()
            .flatMap {
                todaysFixturesResponse = it
                dbManager.insertMatches(it.matches)
            }
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