package com.kryptkode.footballfixtures.app.data.callbacks.base

import android.content.Context
import com.kryptkode.footballfixtures.app.base.BaseTest
import com.kryptkode.footballfixtures.app.data.api.ApiManager
import com.kryptkode.footballfixtures.app.data.db.DbManager
import com.kryptkode.footballfixtures.app.utils.schedulers.AppSchedulers
import io.mockk.mockk

abstract class BaseBoundaryCallbackTest : BaseTest() {

    protected val schedulers: AppSchedulers = mockk()
    protected val apiManager: ApiManager = mockk()
    protected val dbManager: DbManager = mockk()
    protected val context: Context = mockk()

}