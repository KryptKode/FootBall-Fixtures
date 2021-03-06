package com.kryptkode.footballfixtures.app.data.api

import com.kryptkode.footballfixtures.app.data.api.service.FootballService
import retrofit2.Retrofit
import javax.inject.Inject

class ApiManager @Inject constructor(retrofit: Retrofit) {
    private val footballService = retrofit.create(FootballService::class.java)

    fun getCompetitions() = footballService.getCompetitions()

    fun getTodaysFixtures() = footballService.getTodaysFixtures()

    fun getTableForCompetition(competitionId: Int?) =
        footballService.getTableForCompetition(competitionId)

    fun getTeamForCompetition(competitionId: Int?) =
        footballService.getTeamForCompetition(competitionId)

    fun getSquadForTeam(teamId: Int?) = footballService.getSquadForTeam(teamId)

    fun getFixturesForCompetition(competitionId: Int?) =
        footballService.getFixturesForCompetition(competitionId)
}