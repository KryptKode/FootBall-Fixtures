package com.kryptkode.footballfixtures.app.data.callbacks

import android.content.Context
import com.kryptkode.footballfixtures.app.data.api.ApiManager
import com.kryptkode.footballfixtures.app.data.callbacks.base.BaseBoundaryCallback
import com.kryptkode.footballfixtures.app.data.db.DbManager
import com.kryptkode.footballfixtures.app.data.models.table.Standings
import com.kryptkode.footballfixtures.app.data.models.table.Table
import com.kryptkode.footballfixtures.app.data.models.team.Team
import com.kryptkode.footballfixtures.app.utils.ErrorHandler
import com.kryptkode.footballfixtures.app.utils.NetworkState
import com.kryptkode.footballfixtures.app.utils.schedulers.AppSchedulers
import io.reactivex.Observable
import timber.log.Timber
import javax.inject.Inject

class TableBoundaryCallback
@Inject constructor(
    private val schedulers: AppSchedulers,
    private val apiManager: ApiManager,
    private val dbManager: DbManager,
    private val context: Context,
    protected val competitionId: Int?
) : BaseBoundaryCallback<Table>() {

    override fun requestAndSaveData() {
        loadData(false)
    }

    private fun handleError(it: Throwable) {
        Timber.e(it)
        val error = ErrorHandler(context).getErrorMessage(it)
        networkState.postValue(NetworkState.error(error))
    }

    private fun loadData(delete: Boolean) {
        if (networkState.value == NetworkState.LOADING) return
        networkState.postValue(NetworkState.LOADING)
        disposable.add(
            apiManager.getTableForCompetition(competitionId)
                .map {
                    Timber.d("Response: $it")
                    val tableList = mutableListOf<Table>()
                    for (standing in it.standings ?: listOf()) {
                        for (table in standing.table ?: listOf()) {
                            table.competitionId = competitionId
                            Timber.d("Competition ID: ${table.competitionId}")
                            tableList.add(table)
                        }
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
        list: MutableList<Table>
    ): Observable<MutableList<Table>> {
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