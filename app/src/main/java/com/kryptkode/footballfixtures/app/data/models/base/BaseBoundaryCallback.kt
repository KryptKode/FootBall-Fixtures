package com.kryptkode.footballfixtures.app.data.models.base

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.kryptkode.footballfixtures.app.data.api.ApiManager
import com.kryptkode.footballfixtures.app.data.db.DbManager
import com.kryptkode.footballfixtures.app.utils.NetworkState
import com.kryptkode.footballfixtures.app.utils.schedulers.AppSchedulers
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

abstract class BaseBoundaryCallback<T> : PagedList.BoundaryCallback<T>(){

    val disposable = CompositeDisposable()
    val networkState = MutableLiveData<NetworkState>()

    override fun onZeroItemsLoaded() {
        Timber.d(" onZeroItemsLoaded")
        requestAndSaveData()

    }

    override fun onItemAtEndLoaded(itemAtEnd: T) {
        Timber.d(" onItemAtEndLoaded")
        requestAndSaveData()
    }

    abstract fun requestAndSaveData()
}