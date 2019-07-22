package com.kryptkode.footballfixtures.app.utils

enum class Status {
    RUNNING,
    SUCCESS,
    FAILED
}

@Suppress("DataClassPrivateConstructor")
data class NetworkState private constructor(
    val status: Status,
    val msg: Int? = null
) {
    companion object {
        @JvmStatic
        val LOADED = NetworkState(Status.SUCCESS)
        @JvmStatic
        val LOADING = NetworkState(Status.RUNNING)

        @JvmStatic
        fun error(msg: Int?) = NetworkState(Status.FAILED, msg)
    }
}