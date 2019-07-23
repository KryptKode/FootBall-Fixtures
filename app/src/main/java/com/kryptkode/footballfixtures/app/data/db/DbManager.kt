package com.kryptkode.footballfixtures.app.data.db

import androidx.paging.DataSource
import com.kryptkode.footballfixtures.app.data.models.competition.Competition
import com.kryptkode.footballfixtures.app.data.models.fixtures.Match
import com.kryptkode.footballfixtures.app.data.models.squad.Squad
import com.kryptkode.footballfixtures.app.data.models.table.Table
import com.kryptkode.footballfixtures.app.data.models.team.Team
import io.reactivex.Observable

interface DbManager {
    fun insertCompetitions(list: List<Competition>?): Observable<Unit>?
    fun getCompetitions(): DataSource.Factory<Int, Competition>
    fun deleteAllCompetitions(): Observable<Unit>?

    fun insertMatches(list: List<Match>?): Observable<Unit>?
    fun getMatches(): DataSource.Factory<Int, Match>
    fun getMatchesForCompetition(competitionId: Int?): DataSource.Factory<Int, Match>
    fun deleteAllMatches(): Observable<Unit>?


    fun insertTables(list: List<Table>?): Observable<Unit>?
    fun getTables(competitionId: Int?): DataSource.Factory<Int, Table>
    fun deleteAllTables(competitionId: Int?): Observable<Unit>


    fun insertTeams(list: List<Team>?): Observable<Unit>?
    fun getTeams(competitionId: Int?): DataSource.Factory<Int, Team>
    fun deleteAllTeams(): Observable<Unit>?

    fun insertSquad(list: List<Squad>?): Observable<Unit>?
    fun getSquad(teamId: Int?): DataSource.Factory<Int, Squad>
    fun deleteAllSquads(): Observable<Unit>?
}