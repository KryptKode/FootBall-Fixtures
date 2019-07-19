package com.kryptkode.footballfixtures.app.data.db.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Query
import com.kryptkode.footballfixtures.app.data.db.dao.base.BaseDao
import com.kryptkode.footballfixtures.app.data.models.todays.Match

@Dao
interface MatchDao : BaseDao<Match> {

    @Query("SELECT * FROM `match` ORDER BY date DESC")
    fun getMatches(): DataSource.Factory<Int, Match>

    @Query("DELETE FROM `match`")
    fun deleteAll()
}