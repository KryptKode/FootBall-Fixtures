package com.kryptkode.footballfixtures.app.utils.dispatchers

import kotlinx.coroutines.CoroutineDispatcher

data class AppCoroutineDispatchers(
    val network: CoroutineDispatcher,
    val io: CoroutineDispatcher,
    val main: CoroutineDispatcher
)