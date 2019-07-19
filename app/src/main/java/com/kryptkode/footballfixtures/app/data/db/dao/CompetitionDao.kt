package com.kryptkode.footballfixtures.app.data.db.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Query
import com.kryptkode.footballfixtures.app.data.db.dao.base.BaseDao
import com.kryptkode.footballfixtures.app.data.models.competition.Competition


@Dao
interface CompetitionDao : BaseDao<Competition> {

    @Query("SELECT * FROM competition")
    fun getCompetitions(): DataSource.Factory<Int, Competition>

    @Query("DELETE FROM competition")
    fun deleteAll()
}