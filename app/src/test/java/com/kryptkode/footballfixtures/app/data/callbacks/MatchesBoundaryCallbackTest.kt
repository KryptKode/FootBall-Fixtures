package com.kryptkode.footballfixtures.app.data.callbacks

import com.kryptkode.footballfixtures.R
import com.kryptkode.footballfixtures.app.data.callbacks.base.BaseBoundaryCallbackTest
import com.kryptkode.footballfixtures.app.data.mock.MockDataSource
import com.kryptkode.footballfixtures.app.utils.NetworkState
import io.mockk.every
import io.reactivex.Observable
import org.hamcrest.CoreMatchers
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.net.ConnectException
import java.net.HttpURLConnection

class MatchesBoundaryCallbackTest : BaseBoundaryCallbackTest(){
    private lateinit var matchesBoundaryCallback: MatchesBoundaryCallback

    @Before
    override fun setUp() {
        super.setUp()
        matchesBoundaryCallback =
            MatchesBoundaryCallback(schedulers, apiManager, dbManager, errorHandler)
    }

    @Test
    fun `request matches data should be successful`() {

        every {
            dbManager.insertMatches(any())
        } returns Observable.just(Unit)

        mockHttpResponse("today_matches.json", HttpURLConnection.HTTP_OK)

        assertNull(matchesBoundaryCallback.todaysFixturesResponse)

        matchesBoundaryCallback.requestAndSaveData()

        assertThat(matchesBoundaryCallback.todaysFixturesResponse,
            CoreMatchers.`is`(MockDataSource.todaysFixturesResponse)
        )
        assertThat(matchesBoundaryCallback.networkState.value,
            CoreMatchers.`is`(NetworkState.LOADED)
        )
    }


    @Test
    fun `request matches data should fail with network error`() {

        every {
            dbManager.insertCompetitions(any())
        } returns Observable.just(Unit)

        mockHttpResponse("today_matches.json", HttpURLConnection.HTTP_BAD_REQUEST)

        assertNull(matchesBoundaryCallback.todaysFixturesResponse)

        matchesBoundaryCallback.requestAndSaveData()

        assertNull(matchesBoundaryCallback.todaysFixturesResponse)
        assertThat(
            NetworkState.error(R.string.unknown_exception),
            CoreMatchers.`is`(matchesBoundaryCallback.networkState.value)
        )
    }


    @Test
    fun `request matches data should fail with exception`() {

        every {
            dbManager.insertMatches(any())
        } throws ConnectException()

        mockHttpResponse("today_matches.json", HttpURLConnection.HTTP_OK)

        assertNull(matchesBoundaryCallback.todaysFixturesResponse)

        matchesBoundaryCallback.requestAndSaveData()

        assertThat(matchesBoundaryCallback.todaysFixturesResponse,
            CoreMatchers.`is`(MockDataSource.todaysFixturesResponse)
        )
        assertThat(
            NetworkState.error(R.string.connect_exception),
            CoreMatchers.`is`(matchesBoundaryCallback.networkState.value)
        )
    }

}