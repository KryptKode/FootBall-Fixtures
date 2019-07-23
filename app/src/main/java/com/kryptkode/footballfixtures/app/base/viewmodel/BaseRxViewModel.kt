package com.kryptkode.footballfixtures.app.base.viewmodel

import io.reactivex.disposables.CompositeDisposable

class BaseRxViewModel() : BaseViewModel() {
    protected val disposable = CompositeDisposable()

    override fun onCleared() {
        if (!disposable.isDisposed) {
            disposable.dispose()
        }
        super.onCleared()
    }
}