package com.kryptkode.footballfixtures.app.data.callbacks

import com.kryptkode.footballfixtures.R
import com.kryptkode.footballfixtures.app.data.callbacks.base.BaseBoundaryCallbackTest
import com.kryptkode.footballfixtures.app.data.mock.MockDataSource
import com.kryptkode.footballfixtures.app.utils.NetworkState
import io.mockk.every
import io.reactivex.Observable
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertNull
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import java.net.ConnectException
import java.net.HttpURLConnection

class CompetitionBoundaryCallbackTest : BaseBoundaryCallbackTest() {

    private lateinit var competitionBoundaryCallback: CompetitionBoundaryCallback

    @Before
    override fun setUp() {
        super.setUp()
        competitionBoundaryCallback =
            CompetitionBoundaryCallback(schedulers, apiManager, dbManager, errorHandler)
    }

    @Test
    fun `request competition data should be successful`() {

        every {
            dbManager.insertCompetitions(any())
        } returns Observable.just(Unit)

        mockHttpResponse("competitions.json", HttpURLConnection.HTTP_OK)

        assertNull(competitionBoundaryCallback.competitionData)

        competitionBoundaryCallback.requestAndSaveData()

        assertThat(competitionBoundaryCallback.competitionData, `is`(MockDataSource.competitionResponse))
        assertThat(competitionBoundaryCallback.networkState.value, `is`(NetworkState.LOADED))
    }


    @Test
    fun `request competition data should fail with network error`() {

        every {
            dbManager.insertCompetitions(any())
        } returns Observable.just(Unit)

        mockHttpResponse("competitions.json", HttpURLConnection.HTTP_BAD_REQUEST)

        assertNull(competitionBoundaryCallback.competitionData)

        competitionBoundaryCallback.requestAndSaveData()

        assertNull(competitionBoundaryCallback.competitionData)
        assertThat(
            NetworkState.error(R.string.unknown_exception),
            `is`(competitionBoundaryCallback.networkState.value)
        )
    }


    @Test
    fun `request competition data should fail with exception`() {

        every {
            dbManager.insertCompetitions(any())
        } throws ConnectException()

        mockHttpResponse("competitions.json", HttpURLConnection.HTTP_OK)

        assertNull(competitionBoundaryCallback.competitionData)

        competitionBoundaryCallback.requestAndSaveData()

        assertThat(competitionBoundaryCallback.competitionData, `is`(MockDataSource.competitionResponse))
        assertThat(
            NetworkState.error(R.string.connect_exception),
            `is`(competitionBoundaryCallback.networkState.value)
        )
    }
}