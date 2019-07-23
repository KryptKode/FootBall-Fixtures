package com.kryptkode.footballfixtures.app.data.callbacks

import com.kryptkode.footballfixtures.app.data.api.ApiManager
import com.kryptkode.footballfixtures.app.data.api.models.TableResponse
import com.kryptkode.footballfixtures.app.data.callbacks.base.BaseBoundaryCallback
import com.kryptkode.footballfixtures.app.data.db.DbManager
import com.kryptkode.footballfixtures.app.data.models.table.Standings
import com.kryptkode.footballfixtures.app.data.models.table.Table
import com.kryptkode.footballfixtures.app.utils.ErrorHandler
import com.kryptkode.footballfixtures.app.utils.NetworkState
import com.kryptkode.footballfixtures.app.utils.schedulers.AppSchedulers
import io.reactivex.Observable
import timber.log.Timber
import javax.inject.Inject

class TableBoundaryCallback
@Inject constructor(
    protected val competitionId: Int?,
    schedulers: AppSchedulers,
    apiManager: ApiManager,
    dbManager: DbManager,
    errorHandler: ErrorHandler
) : BaseBoundaryCallback<Table>(schedulers, apiManager, dbManager, errorHandler) {

    var tableResponse : TableResponse? = null

    override fun requestAndSaveData() {
        loadData(false)
    }

    private fun handleError(it: Throwable) {
        Timber.e(it)
        val error = errorHandler.getErrorMessage(it)
        networkState.postValue(NetworkState.error(error))
    }

    private fun loadData(delete: Boolean) {
        if (networkState.value == NetworkState.LOADING) return
        networkState.postValue(NetworkState.LOADING)
        disposable.add(
            apiManager.getTableForCompetition(competitionId)
                .map {
                    tableResponse = it
                    Timber.d("Response: $it")
                    val standing = if (it.standings?.size ?: 0 > 0) {
                        it.standings?.get(0)
                    } else {
                        Standings(mutableListOf())
                    }

                    val tableList = standing?.table ?: mutableListOf()
                    for (i in tableList) {
                        i.competitionId = competitionId
                    }
                    tableList
                }
                .concatMap {
                    if (delete) {
                        deleteAndReturn(competitionId, it)
                    } else {
                        Observable.just(it)
                    }
                }
                .flatMap {
                    Timber.d("Table: $it")
                    dbManager.insertTables(it)
                }
                .subscribeOn(schedulers.network).subscribe({
                    handleSuccess()
                }, {
                    handleError(it)
                }, {
                    handleSuccess()
                })
        )
    }

    private fun deleteAndReturn(
        competitionId: Int?,
        list: List<Table>
    ): Observable<List<Table>> {
        return dbManager.deleteAllTables(competitionId)
            .map {
                list
            }

    }

    fun deleteAndRefresh() {
        loadData(true)
    }

    private fun handleSuccess() {
        networkState.postValue(NetworkState.LOADED)
    }
}