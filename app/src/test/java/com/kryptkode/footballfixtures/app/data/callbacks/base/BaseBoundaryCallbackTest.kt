package com.kryptkode.footballfixtures.app.data.callbacks.base

import com.kryptkode.footballfixtures.app.base.BaseTest
import com.kryptkode.footballfixtures.app.data.api.ApiManager
import com.kryptkode.footballfixtures.app.data.db.DbManager
import com.kryptkode.footballfixtures.app.data.mock.MockDataSource.getJson
import com.kryptkode.footballfixtures.app.di.DaggerTestAppComponent
import com.kryptkode.footballfixtures.app.utils.ErrorHandler
import com.kryptkode.footballfixtures.app.utils.schedulers.AppSchedulers
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import javax.inject.Inject

abstract class BaseBoundaryCallbackTest : BaseTest() {

    @Inject
    lateinit var errorHandler: ErrorHandler

    @Inject
    lateinit var schedulers: AppSchedulers

    @Inject
    lateinit var apiManager: ApiManager

    @Inject
    lateinit var dbManager: DbManager

    @Inject
    lateinit var mockServer: MockWebServer

    @Before
    open fun setUp() {
        configureDi()
    }


    private fun configureDi() {
        val testAppComponent = DaggerTestAppComponent.builder()
            .build()
        testAppComponent.inject(this)
    }

    @After
    fun tearDown() {
        stopMockServer()
    }

    /**
    * Stops the mock server that was instantiated and started by injection
    * */
    private fun stopMockServer() {
        mockServer.shutdown()
    }

    fun mockHttpResponse(fileName: String, responseCode: Int) = mockServer.enqueue(
        MockResponse()
            .setResponseCode(responseCode)
            .setBody(getJson(fileName))
    )
}