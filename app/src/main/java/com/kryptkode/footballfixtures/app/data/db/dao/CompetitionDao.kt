package com.kryptkode.footballfixtures.app.data.db.dao

import androidx.annotation.VisibleForTesting
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Query
import com.kryptkode.footballfixtures.app.data.db.dao.base.BaseDao
import com.kryptkode.footballfixtures.app.data.models.competition.Competition


@Dao
abstract class CompetitionDao : BaseDao<Competition> {

    @Query("SELECT * FROM competition")
   abstract fun getCompetitions(): DataSource.Factory<Int, Competition>

    @Query("SELECT * FROM `competition`")
    @VisibleForTesting
    abstract fun getCompetitionsList(): List<Competition>

    @Query("DELETE FROM competition")
    abstract fun deleteAll()
}