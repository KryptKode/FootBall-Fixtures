package com.kryptkode.footballfixtures.app.data.repo

import com.kryptkode.footballfixtures.app.data.models.Listing
import com.kryptkode.footballfixtures.app.data.models.competition.Competition
import com.kryptkode.footballfixtures.app.data.models.fixtures.Match
import com.kryptkode.footballfixtures.app.data.models.squad.Squad
import com.kryptkode.footballfixtures.app.data.models.table.Table
import com.kryptkode.footballfixtures.app.data.models.team.Team

interface Repository {
    fun getCompetitions(): Listing<Competition>
    fun getMatches(): Listing<Match>
    fun getTableForCompetition(competitionId: Int?): Listing<Table>
    fun getTeamsForCompetition(competitionId: Int?): Listing<Team>
    fun getSquadForTeam(teamId: Int?): Listing<Squad>
    fun getFixturesForCompetition(competitionId: Int?): Listing<Match>
}