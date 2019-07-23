package com.kryptkode.footballfixtures.app.data.callbacks

import com.kryptkode.footballfixtures.R
import com.kryptkode.footballfixtures.app.data.callbacks.base.BaseBoundaryCallbackTest
import com.kryptkode.footballfixtures.app.data.mock.MockDataSource
import com.kryptkode.footballfixtures.app.data.mock.MockDataSource.competitionId
import com.kryptkode.footballfixtures.app.utils.NetworkState
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Observable
import org.hamcrest.CoreMatchers
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.net.ConnectException
import java.net.HttpURLConnection

class FixturesBoundaryCallbackTest : BaseBoundaryCallbackTest(){
    private lateinit var fixturesBoundaryCallback: FixturesBoundaryCallback

    @Before
    override fun setUp() {
        super.setUp()
        fixturesBoundaryCallback =
            FixturesBoundaryCallback(competitionId, schedulers, apiManager, dbManager, errorHandler)
    }

    @Test
    fun `request fixtures data should be successful`() {

        every {
            dbManager.insertMatches(any())
        } returns Observable.just(Unit)

        mockHttpResponse("fixtures.json", HttpURLConnection.HTTP_OK)

        assertNull(fixturesBoundaryCallback.fixturesResponse)

        fixturesBoundaryCallback.requestAndSaveData()

        assertThat(fixturesBoundaryCallback.fixturesResponse,
            CoreMatchers.`is`(MockDataSource.fixturesResponse)
        )
        assertThat(fixturesBoundaryCallback.networkState.value,
            CoreMatchers.`is`(NetworkState.LOADED)
        )
    }


    @Test
    fun `request fixtures data should fail with network error`() {

        every {
            dbManager.insertCompetitions(any())
        } returns Observable.just(Unit)

        mockHttpResponse("fixtures.json", HttpURLConnection.HTTP_BAD_REQUEST)

        assertNull(fixturesBoundaryCallback.fixturesResponse)

        fixturesBoundaryCallback.requestAndSaveData()

        assertNull(fixturesBoundaryCallback.fixturesResponse)
        assertThat(
            NetworkState.error(R.string.unknown_exception),
            CoreMatchers.`is`(fixturesBoundaryCallback.networkState.value)
        )
    }


    @Test
    fun `request fixtures data should fail with exception`() {

        every {
            dbManager.insertMatches(any())
        } throws ConnectException()

        mockHttpResponse("fixtures.json", HttpURLConnection.HTTP_OK)

        assertNull(fixturesBoundaryCallback.fixturesResponse)

        fixturesBoundaryCallback.requestAndSaveData()

        assertThat(fixturesBoundaryCallback.fixturesResponse,
            CoreMatchers.`is`(MockDataSource.fixturesResponse)
        )
        assertThat(
            NetworkState.error(R.string.connect_exception),
            CoreMatchers.`is`(fixturesBoundaryCallback.networkState.value)
        )
    }

}