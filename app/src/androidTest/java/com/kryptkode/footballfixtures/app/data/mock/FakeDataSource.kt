package com.kryptkode.footballfixtures.app.data.mock

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.google.gson.Gson
import com.kryptkode.footballfixtures.R
import com.kryptkode.footballfixtures.app.data.api.models.*
import com.kryptkode.footballfixtures.app.data.models.competition.Competition
import com.kryptkode.footballfixtures.app.data.models.fixtures.Match
import com.kryptkode.footballfixtures.app.data.models.squad.Squad
import com.kryptkode.footballfixtures.app.data.models.table.Table
import com.kryptkode.footballfixtures.app.data.models.team.Team
import com.kryptkode.footballfixtures.app.utils.AssetsLoader
import com.kryptkode.footballfixtures.app.utils.NetworkState
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

class FakeDataSource @Inject constructor(private val assetsLoader: AssetsLoader, val gson: Gson) {

    fun getFakeMatches(size: Int): List<Match> {
        val data = assetsLoader.getRawResourceDataAsString("today_matches")
        return gson.fromJson(data, TodaysFixturesResponse::class.java).matches.subList(0, size)
    }

    fun getCompetitions(size: Int): List<Competition>? {
        val data = assetsLoader.getRawResourceDataAsString("competitions")
        return gson.fromJson(data, CompetitionResponse::class.java).competitions?.subList(0, size)
    }


    fun getFakeSquad(size: Int): List<Squad>? {
        val data = assetsLoader.getRawResourceDataAsString("teams_detail")
        return gson.fromJson(data, SquadResponse::class.java).squad?.subList(0, size)
    }

    fun getFakeTable(size: Int): List<Table>? {
        val data = assetsLoader.getRawResourceDataAsString("standings")
        return gson.fromJson(data, TableResponse::class.java).standings?.get(0)?.table?.subList(
            0,
            size
        )
    }

    fun getFakeTeams(size: Int): List<Team>? {
        val data = assetsLoader.getRawResourceDataAsString("teams")
        return gson.fromJson(data, TeamResponse::class.java).teams?.subList(0, size)
    }
}

class FakeKeyedDataSource<T>(val list: List<T>?) : PageKeyedDataSource<Int, T>() {
    val disposable = CompositeDisposable()
    val networkState = MutableLiveData<NetworkState>()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, T>
    ) {
        if (networkState.value == NetworkState.LOADING) {
            return
        }
        networkState.postValue(NetworkState.LOADING)
        try {
            callback.onResult(list ?: listOf(), null, null)
            networkState.postValue(NetworkState.LOADED)
        } catch (e: Exception) {
            Timber.e(e)
            networkState.postValue(NetworkState.error(R.string.unknown_exception))
        }

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        if (networkState.value == NetworkState.LOADING) {
            return
        }
        networkState.value = NetworkState.LOADING
        try {
            callback.onResult(list ?: listOf(), null)
            networkState.value = NetworkState.LOADED
        } catch (e: Exception) {
            Timber.e(e)
            networkState.value = NetworkState.error(R.string.unknown_exception)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        networkState.value = NetworkState.LOADING
        try {
            callback.onResult(list ?: listOf(), null)
            networkState.value = NetworkState.LOADED
        } catch (e: Exception) {
            Timber.e(e)
            networkState.value = NetworkState.error(R.string.unknown_exception)
        }
    }
}

class FakeDataSourceFactory<T>(list: List<T>?) : DataSource.Factory<Int, T>() {
    val factory = FakeKeyedDataSource(list)
    override fun create(): DataSource<Int, T> {
        return factory
    }
}