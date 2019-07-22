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

class TeamBoundaryCallbackTest : BaseBoundaryCallbackTest() {

    private lateinit var teamBoundaryCallback: TeamBoundaryCallback

    @Before
    override fun setUp() {
        super.setUp()
        teamBoundaryCallback =
            TeamBoundaryCallback(competitionId, schedulers, apiManager, dbManager, errorHandler)
    }

    @Test
    fun `request competition data should be successful`() {

        every {
            dbManager.insertTeams(any())
        } returns Observable.just(Unit)

        mockHttpResponse("teams.json", HttpURLConnection.HTTP_OK)

        assertNull(teamBoundaryCallback.teamResponse)

        teamBoundaryCallback.requestAndSaveData()

        assertThat(teamBoundaryCallback.teamResponse,
            CoreMatchers.`is`(MockDataSource.teamResponse)
        )
        assertThat(teamBoundaryCallback.networkState.value,
            CoreMatchers.`is`(NetworkState.LOADED)
        )
    }


    @Test
    fun `request competition data should fail with network error`() {

        every {
            dbManager.insertTeams(any())
        } returns Observable.just(Unit)

        mockHttpResponse("teams.json", HttpURLConnection.HTTP_BAD_REQUEST)

        assertNull(teamBoundaryCallback.teamResponse)

        teamBoundaryCallback.requestAndSaveData()

        assertNull(teamBoundaryCallback.teamResponse)
        assertThat(
            NetworkState.error(R.string.unknown_exception),
            CoreMatchers.`is`(teamBoundaryCallback.networkState.value)
        )
    }


    @Test
    fun `request competition data should fail with exception`() {

        every {
            dbManager.insertTeams(any())
        } throws ConnectException()

        mockHttpResponse("teams.json", HttpURLConnection.HTTP_OK)

        assertNull(teamBoundaryCallback.teamResponse)

        teamBoundaryCallback.requestAndSaveData()

        assertThat(teamBoundaryCallback.teamResponse,
            CoreMatchers.`is`(MockDataSource.teamResponse)
        )
        assertThat(
            NetworkState.error(R.string.connect_exception),
            CoreMatchers.`is`(teamBoundaryCallback.networkState.value)
        )
    }
}