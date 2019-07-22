package com.kryptkode.footballfixtures.app.data.callbacks

import androidx.annotation.VisibleForTesting
import com.kryptkode.footballfixtures.app.data.api.ApiManager
import com.kryptkode.footballfixtures.app.data.api.models.CompetitionResponse
import com.kryptkode.footballfixtures.app.data.db.DbManager
import com.kryptkode.footballfixtures.app.data.callbacks.base.BaseBoundaryCallback
import com.kryptkode.footballfixtures.app.data.models.competition.Competition
import com.kryptkode.footballfixtures.app.utils.ErrorHandler
import com.kryptkode.footballfixtures.app.utils.NetworkState
import com.kryptkode.footballfixtures.app.utils.schedulers.AppSchedulers
import timber.log.Timber
import javax.inject.Inject


/**
 * This boundary callback gets notified when user reaches to the edges of the list for example when
 * the database cannot provide any more data
 **/
class CompetitionBoundaryCallback @Inject constructor(
    schedulers: AppSchedulers,
    apiManager: ApiManager,
    dbManager: DbManager,
    errorHandler: ErrorHandler
) : BaseBoundaryCallback<Competition>(schedulers, apiManager, dbManager, errorHandler) {

    @VisibleForTesting
    var competitionData: CompetitionResponse? = null

    override fun requestAndSaveData() {
        if (networkState.value == NetworkState.LOADING) return
        networkState.postValue(NetworkState.LOADING)
        disposable.add(apiManager.getCompetitions()
            .flatMap {
                competitionData = it
                dbManager.insertCompetitions(it.competitions)
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