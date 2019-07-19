package com.kryptkode.footballfixtures.app.data.db

import androidx.paging.DataSource
import com.kryptkode.footballfixtures.app.data.models.competition.Competition
import com.kryptkode.footballfixtures.app.data.models.todays.Match
import com.kryptkode.footballfixtures.app.utils.schedulers.AppSchedulers
import io.reactivex.Observable
import javax.inject.Inject

class DbManager @Inject constructor(private val schedulers:AppSchedulers,
                                    db: AppDatabase) {
    private val competitionDao = db.competitionDao()
    private val matchDao = db.matchDao()

    fun insertCompetitions(list: List<Competition>? ): Observable<Unit>? {
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

    fun insertMatches(list: List<Match>? ): Observable<Unit>? {
        return Observable.fromCallable { matchDao.insert(list ?: listOf()) }
            .subscribeOn(schedulers.io)
    }

    fun getMatches(): DataSource.Factory<Int, Match> {
        return matchDao.getMatches()
    }

    fun deleteAllMatches(): Observable<Unit>? {
        return Observable.fromCallable { matchDao.deleteAll() }
            .subscribeOn(schedulers.io)
    }

}
