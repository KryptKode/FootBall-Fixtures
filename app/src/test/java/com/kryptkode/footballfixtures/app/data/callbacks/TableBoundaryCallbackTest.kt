package com.kryptkode.footballfixtures.app.data.callbacks

import com.kryptkode.footballfixtures.R
import com.kryptkode.footballfixtures.app.data.callbacks.base.BaseBoundaryCallbackTest
import com.kryptkode.footballfixtures.app.data.mock.MockDataSource
import com.kryptkode.footballfixtures.app.data.mock.MockDataSource.competitionId
import com.kryptkode.footballfixtures.app.utils.NetworkState
import io.mockk.every
import io.reactivex.Observable
import org.hamcrest.CoreMatchers
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.net.ConnectException
import java.net.HttpURLConnection

class TableBoundaryCallbackTest : BaseBoundaryCallbackTest() {

    private lateinit var tableBoundaryCallback: TableBoundaryCallback

    @Before
    override fun setUp() {
        super.setUp()
        tableBoundaryCallback =
            TableBoundaryCallback(competitionId, schedulers, apiManager, dbManager, errorHandler)
    }

    @Test
    fun `request table data should be successful`() {

        every {
            dbManager.insertTables(any())
        } returns Observable.just(Unit)

        mockHttpResponse("standings.json", HttpURLConnection.HTTP_OK)

        assertNull(tableBoundaryCallback.tableResponse)

        tableBoundaryCallback.requestAndSaveData()

        assertThat(tableBoundaryCallback.tableResponse,
            CoreMatchers.`is`(MockDataSource.tableResponse)
        )
        assertThat(tableBoundaryCallback.networkState.value,
            CoreMatchers.`is`(NetworkState.LOADED)
        )
    }


    @Test
    fun `request table data should fail with network error`() {

        every {
            dbManager.insertTables(any())
        } returns Observable.just(Unit)

        mockHttpResponse("standings.json", HttpURLConnection.HTTP_BAD_REQUEST)

        assertNull(tableBoundaryCallback.tableResponse)

        tableBoundaryCallback.requestAndSaveData()

        assertNull(tableBoundaryCallback.tableResponse)
        assertThat(
            NetworkState.error(R.string.unknown_exception),
            CoreMatchers.`is`(tableBoundaryCallback.networkState.value)
        )
    }


    @Test
    fun `request table data should fail with exception`() {

        every {
            dbManager.insertTables(any())
        } throws ConnectException()

        mockHttpResponse("standings.json", HttpURLConnection.HTTP_OK)

        assertNull(tableBoundaryCallback.tableResponse)

        tableBoundaryCallback.requestAndSaveData()

        assertThat(tableBoundaryCallback.tableResponse,
            CoreMatchers.`is`(MockDataSource.tableResponse)
        )
        assertThat(
            NetworkState.error(R.string.connect_exception),
            CoreMatchers.`is`(tableBoundaryCallback.networkState.value)
        )
    }
}