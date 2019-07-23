package com.kryptkode.footballfixtures.app.utils.schedulers

import io.reactivex.Scheduler

data class AppSchedulers(
    val network: Scheduler,
    val io: Scheduler,
    val main: Scheduler
)