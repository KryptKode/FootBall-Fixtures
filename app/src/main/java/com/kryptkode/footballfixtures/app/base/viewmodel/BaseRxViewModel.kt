package com.kryptkode.footballfixtures.app.base.viewmodel

import android.app.Application
import io.reactivex.disposables.CompositeDisposable

class BaseRxViewModel(application: Application) : BaseViewModel(application) {
    protected val disposable = CompositeDisposable()

    override fun onCleared() {
        if (!disposable.isDisposed) {
            disposable.dispose()
        }
        super.onCleared()
    }
}