package com.kryptkode.footballfixtures.app.data.api.service

import com.kryptkode.footballfixtures.BuildConfig
import com.kryptkode.footballfixtures.app.data.api.models.CompetitionResponse
import com.kryptkode.footballfixtures.app.data.api.models.TableResponse
import com.kryptkode.footballfixtures.app.data.api.models.TodaysFixturesResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface FootballService {

    @GET("competitions?plan=TIER_ONE")
    @Headers("X-Auth-Token: ${BuildConfig.API_TOKEN}")
    fun getCompetitions(): Observable<CompetitionResponse>

    @GET("matches?dateFrom=2019-05-05&dateTo=2019-05-15")
    @Headers("X-Auth-Token: ${BuildConfig.API_TOKEN}")
    fun getTodaysFixtures(): Observable<TodaysFixturesResponse>

    @GET("competitions/{competitionId}/standings")
    @Headers("X-Auth-Token: ${BuildConfig.API_TOKEN}")
    fun getTableForCompetition(@Path("competitionId") competitionId: Int?): Observable<TableResponse>
}

