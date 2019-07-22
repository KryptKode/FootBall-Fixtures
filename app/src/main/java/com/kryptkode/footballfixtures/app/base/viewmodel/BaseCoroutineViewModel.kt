package com.kryptkode.footballfixtures.app.base.viewmodel

import com.kryptkode.footballfixtures.app.utils.dispatchers.AppCoroutineDispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseCoroutineViewModel(
    protected val dispatchers: AppCoroutineDispatchers
) : BaseViewModel(), CoroutineScope {


    protected val job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + dispatchers.main

    override fun onCleared() {
        if (!job.isCancelled) {
            job.cancel()
        }
        super.onCleared()
    }


}