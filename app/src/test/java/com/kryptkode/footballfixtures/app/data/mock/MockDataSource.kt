package com.kryptkode.footballfixtures.app.data.mock

import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.google.gson.Gson
import com.kryptkode.footballfixtures.app.data.api.models.*
import com.kryptkode.footballfixtures.app.data.callbacks.MatchesBoundaryCallback
import com.kryptkode.footballfixtures.app.data.models.Listing
import com.kryptkode.footballfixtures.app.data.models.fixtures.Match
import com.kryptkode.footballfixtures.app.data.repo.AppRepository
import java.io.File

object MockDataSource {

    val competitionResponse by lazy {
        getCompetitionData()
    }

    val todaysFixturesResponse by lazy {
        getMatchesData()
    }

    const val competitionId = 2013
    val fixturesResponse by lazy {
        getFixturesData()
    }

    const val teamId = 2048
    val squadResponse by lazy {
        getSquadData()
    }

     val tableResponse by lazy {
        getTableData()
    }


    val teamResponse by lazy {
        getTeamData()
    }
    private fun getCompetitionData(): CompetitionResponse {
        val data = getJson("competitions.json")
        return Gson().fromJson(data, CompetitionResponse::class.java)
    }

    private fun getMatchesData(): TodaysFixturesResponse {
        val data = getJson("today_matches.json")
        return Gson().fromJson(data, TodaysFixturesResponse::class.java)
    }

    private fun getFixturesData(): FixturesResponse {
        val data = getJson("fixtures.json")
        return Gson().fromJson(data, FixturesResponse::class.java).apply {
            val matchList = this.matches
            for (i in matchList) {
                i.competitionId = competitionId
            }
        }
    }


    private fun getSquadData(): SquadResponse {
        val data = getJson("squad.json")
        return Gson().fromJson(data, SquadResponse::class.java).apply {
            val squadList = this.squad
            for (i in squadList ?: mutableListOf()) {
                i.teamId = teamId
            }
        }
    }

    private fun getTableData(): TableResponse {
        val data = getJson("standings.json")
        return Gson().fromJson(data, TableResponse::class.java).apply {
            val standingsList = this.standings
            if(standingsList?.isNotEmpty() == true){
                for (i in standingsList.get(0).table ?: mutableListOf()) {
                    i.competitionId = competitionId
                }

            }
        }
    }

    private fun getTeamData(): TeamResponse {
        val data = getJson("teams.json")
        return Gson().fromJson(data, TeamResponse::class.java).apply {
            val teamList = this.teams
            for (i in teamList ?: mutableListOf()) {
                i.competitionId = competitionId
            }
        }
    }

    fun getJson(path: String): String {
        val uri = this.javaClass.classLoader?.getResource(path)
        val file = File(uri?.path ?: "")
        return String(file.readBytes())
    }

}

