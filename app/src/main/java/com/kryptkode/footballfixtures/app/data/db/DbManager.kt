package com.kryptkode.footballfixtures.app.data.db

import androidx.paging.DataSource
import com.kryptkode.footballfixtures.app.data.models.competition.Competition
import com.kryptkode.footballfixtures.app.data.models.fixtures.Match
import com.kryptkode.footballfixtures.app.data.models.squad.Squad
import com.kryptkode.footballfixtures.app.data.models.table.Table
import com.kryptkode.footballfixtures.app.data.models.team.Team
import com.kryptkode.footballfixtures.app.utils.schedulers.AppSchedulers
import io.reactivex.Observable
import javax.inject.Inject

class DbManager @Inject constructor(
    private val schedulers: AppSchedulers,
    db: AppDatabase
) {
    private val competitionDao = db.competitionDao()
    private val matchDao = db.matchDao()
    private val tableDao = db.tableDao()
    private val teamsDao = db.teamsDao()
    private val squadDao = db.squadDao()

    fun insertCompetitions(list: List<Competition>?): Observable<Unit>? {
        return Observable.fromCallable { competitionDao.insert(list ?: listOf()) }
            .subscribeOn(schedulers.io)
    }

    fun getCompetitions(): DataSource.Factory<Int, Competition> {
        return competitionDao.getCompetitions()
    }

    fun deleteAllCompetitions(): Observable<Unit>? {
        return Observable.fromCallable { competitionDao.deleteAll() }
            .subscribeOn(schedulers.io)
    }

    fun insertMatches(list: List<Match>?): Observable<Unit>? {
        return Observable.fromCallable { matchDao.insert(list ?: listOf()) }
            .subscribeOn(schedulers.io)
    }

    fun getMatches(): DataSource.Factory<Int, Match> {
        return matchDao.getMatches()
    }

    fun getMatchesForCompetition(competitionId: Int?): DataSource.Factory<Int, Match> {
        return matchDao.getMatchesForCompetition(competitionId)
    }

    fun deleteAllMatches(): Observable<Unit>? {
        return Observable.fromCallable { matchDao.deleteAll() }
            .subscribeOn(schedulers.io)
    }

    fun insertTables(list: List<Table>?): Observable<Unit>? {
        return Observable.fromCallable { tableDao.insert(list ?: listOf()) }
            .subscribeOn(schedulers.io)
    }

    fun getTables(competitionId: Int?): DataSource.Factory<Int, Table> {
        return tableDao.getTableForCompetition(competitionId)
    }

    fun deleteAllTables(competitionId: Int?): Observable<Unit> {
        return Observable.fromCallable { tableDao.deleteAll(competitionId) }
            .subscribeOn(schedulers.io)
    }


    fun insertTeams(list: List<Team>?): Observable<Unit>? {
        return Observable.fromCallable { teamsDao.insert(list ?: listOf()) }
            .subscribeOn(schedulers.io)
    }

    fun getTeams(competitionId: Int?): DataSource.Factory<Int, Team> {
        return teamsDao.getTeamsForCompetition(competitionId)
    }

    fun deleteAllTeams(): Observable<Unit>? {
        return Observable.fromCallable { teamsDao.deleteAll() }
            .subscribeOn(schedulers.io)
    }

    fun insertSquad(list: List<Squad>?): Observable<Unit>? {
        return Observable.fromCallable { squadDao.insert(list ?: listOf()) }
            .subscribeOn(schedulers.io)
    }

    fun getSquad(teamId: Int?): DataSource.Factory<Int, Squad> {
        return squadDao.getSquadForCompetition(teamId)
    }

    fun deleteAllSquads(): Observable<Unit>? {
        return Observable.fromCallable { squadDao.deleteAll() }
            .subscribeOn(schedulers.io)
    }
}
