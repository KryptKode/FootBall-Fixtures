package com.kryptkode.footballfixtures.app.data.api

import com.kryptkode.footballfixtures.app.base.BaseTest
import com.kryptkode.footballfixtures.app.data.api.models.CompetitionResponse
import com.kryptkode.footballfixtures.app.data.mock.MockDataSource
import com.kryptkode.footballfixtures.app.di.DaggerTestAppComponent
import io.reactivex.observers.TestObserver
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import java.net.HttpURLConnection
import javax.inject.Inject

class ApiManagerTest : BaseTest() {

    @Inject
    lateinit var mockServer: MockWebServer

    @Inject
    lateinit var apiManager: ApiManager

    val testSubscriber = TestObserver<CompetitionResponse>()

    @Before
     fun setUp() {
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

    private fun stopMockServer() {
        mockServer.shutdown()
    }

    private fun mockHttpResponse(fileName: String, responseCode: Int) = mockServer.enqueue(
        MockResponse()
            .setResponseCode(responseCode)
            .setBody(MockDataSource.getJson(fileName))
    )

    @Test
    fun `get competitions is successful`() {

        mockHttpResponse("competitions.json", HttpURLConnection.HTTP_OK)

        apiManager.getCompetitions()
            .subscribe(testSubscriber)

        testSubscriber.assertValue(MockDataSource.competitionResponse)
        testSubscriber.assertNoErrors()
    }


    @Test
    fun `get competitions fails with network errors`() {

        mockHttpResponse("competitions.json", HttpURLConnection.HTTP_GATEWAY_TIMEOUT)

        apiManager.getCompetitions()
            .subscribe(testSubscriber)

        testSubscriber.assertError(HttpException::class.java)
    }

}