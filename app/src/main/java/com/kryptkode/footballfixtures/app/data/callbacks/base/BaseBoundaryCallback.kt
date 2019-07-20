package com.kryptkode.footballfixtures.app.data.callbacks.base

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.kryptkode.footballfixtures.app.utils.NetworkState
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
//        requestAndSaveData()
    }

    abstract fun requestAndSaveData()

}