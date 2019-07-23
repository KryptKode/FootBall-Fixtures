package com.kryptkode.footballfixtures.app.data.callbacks

import com.kryptkode.footballfixtures.R
import com.kryptkode.footballfixtures.app.data.callbacks.base.BaseBoundaryCallbackTest
import com.kryptkode.footballfixtures.app.data.mock.MockDataSource
import com.kryptkode.footballfixtures.app.data.mock.MockDataSource.teamId
import com.kryptkode.footballfixtures.app.utils.NetworkState
import io.mockk.every
import io.reactivex.Observable
import org.hamcrest.CoreMatchers
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.net.ConnectException
import java.net.HttpURLConnection

class SquadBoundaryCallbackTest : BaseBoundaryCallbackTest() {

    private lateinit var squadBoundaryCallback: SquadBoundaryCallback

    @Before
    override fun setUp() {
        super.setUp()
        squadBoundaryCallback =
            SquadBoundaryCallback(teamId, schedulers, apiManager, dbManager, errorHandler)
    }

    @Test
    fun `request squad data should be successful`() {

        every {
            dbManager.insertSquad(any())
        } returns Observable.just(Unit)

        mockHttpResponse("squad.json", HttpURLConnection.HTTP_OK)

        assertNull(squadBoundaryCallback.squadResponse)

        squadBoundaryCallback.requestAndSaveData()

        assertThat(squadBoundaryCallback.squadResponse,
            CoreMatchers.`is`(MockDataSource.squadResponse)
        )
        assertThat(squadBoundaryCallback.networkState.value,
            CoreMatchers.`is`(NetworkState.LOADED)
        )
    }


    @Test
    fun `request squad data should fail with network error`() {

        every {
            dbManager.insertCompetitions(any())
        } returns Observable.just(Unit)

        mockHttpResponse("squad.json", HttpURLConnection.HTTP_BAD_REQUEST)

        assertNull(squadBoundaryCallback.squadResponse)

        squadBoundaryCallback.requestAndSaveData()

        assertNull(squadBoundaryCallback.squadResponse)
        assertThat(
            NetworkState.error(R.string.unknown_exception),
            CoreMatchers.`is`(squadBoundaryCallback.networkState.value)
        )
    }


    @Test
    fun `request squad data should fail with exception`() {

        every {
            dbManager.insertSquad(any())
        } throws ConnectException()

        mockHttpResponse("squad.json", HttpURLConnection.HTTP_OK)

        assertNull(squadBoundaryCallback.squadResponse)

        squadBoundaryCallback.requestAndSaveData()

        assertThat(squadBoundaryCallback.squadResponse,
            CoreMatchers.`is`(MockDataSource.squadResponse)
        )
        assertThat(
            NetworkState.error(R.string.connect_exception),
            CoreMatchers.`is`(squadBoundaryCallback.networkState.value)
        )
    }
}