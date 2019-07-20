package com.kryptkode.footballfixtures.app.data.db.dao

import androidx.annotation.VisibleForTesting
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Query
import com.kryptkode.footballfixtures.app.data.db.dao.base.BaseDao
import com.kryptkode.footballfixtures.app.data.models.todays.Match

@Dao
abstract class MatchDao : BaseDao<Match> {

    @Query("SELECT * FROM `match` ORDER BY date DESC")
    abstract fun getMatches(): DataSource.Factory<Int, Match>

    @Query("SELECT * FROM `match` ORDER BY date DESC")
    @VisibleForTesting
    abstract fun getMatchesList(): List<Match>

    @Query("DELETE FROM `match`")
    abstract fun deleteAll()
}