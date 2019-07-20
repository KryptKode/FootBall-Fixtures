package com.kryptkode.footballfixtures.app.utils


import com.kryptkode.footballfixtures.BuildConfig

object Constants {
    const val PREFS_NAME = "prefs"
    const val EXTRAS = "extras"
    var TESTING = BuildConfig.DEBUG
    const val IMAGE_MIME_TYPE = "image/*"
    const val TEXT_MIME_TYPE = "text/*"
    const val PROVIDER = ".provider"

    const val OK_HTTP_CACHE: String = "ok_http_cache"
    const val CONNECT_TIME_OUT = 60L
    const val WRITE_TIME_OUT = 60L
    const val DATABASE_NAME = "football.io"
    const val REGULAR = "REGULAR"
    const val COMPETITION_DETAIL = "COMPETITION_DETAIL"
}