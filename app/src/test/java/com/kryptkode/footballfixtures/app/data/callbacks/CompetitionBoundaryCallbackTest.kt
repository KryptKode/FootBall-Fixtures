package com.kryptkode.footballfixtures.app.data.callbacks

import com.kryptkode.footballfixtures.app.data.callbacks.base.BaseBoundaryCallbackTest
import com.kryptkode.footballfixtures.app.data.mock.MockDataSource
import io.mockk.every
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import org.junit.Assert.*
import org.junit.Test

class CompetitionBoundaryCallbackTest : BaseBoundaryCallbackTest() {

    private val competitionBoundaryCallback by lazy {
        CompetitionBoundaryCallback(schedulers, apiManager, dbManager, context)
    }


    @Test
    fun `request data should return a successful response`() {
        every {
            apiManager.getCompetitions()
        } returns MockDataSource.competitonData

        every {
            dbManager.insertCompetitions(any())
        } returns Observable.just(Unit)

        every {
            schedulers.network
        }returns AndroidSchedulers.mainThread()

        competitionBoundaryCallback.requestAndSaveData()


    }

}