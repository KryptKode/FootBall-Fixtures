package com.kryptkode.footballfixtures.app.data.callbacks

import android.content.Context
import com.kryptkode.footballfixtures.app.data.api.ApiManager
import com.kryptkode.footballfixtures.app.data.callbacks.base.BaseBoundaryCallback
import com.kryptkode.footballfixtures.app.data.db.DbManager
import com.kryptkode.footballfixtures.app.data.models.table.Standings
import com.kryptkode.footballfixtures.app.data.models.table.Table
import com.kryptkode.footballfixtures.app.utils.ErrorHandler
import com.kryptkode.footballfixtures.app.utils.NetworkState
import com.kryptkode.footballfixtures.app.utils.schedulers.AppSchedulers
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
        if (networkState.value == NetworkState.LOADING) return
        networkState.postValue(NetworkState.LOADING)
        disposable.add(apiManager.getTableForCompetition(competitionId)
            .map {

                val tableList = mutableListOf<Table>()
                for (standing in it.standings ?: listOf()) {
                    for (table in standing.table ?: listOf()) {
                        table.competitionId = competitionId
                        tableList.add(table)
                    }
                }
                tableList
            }
            .flatMap { dbManager.insertTables(it) }
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