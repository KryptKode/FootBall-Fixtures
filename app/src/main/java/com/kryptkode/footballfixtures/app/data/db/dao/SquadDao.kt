package com.kryptkode.footballfixtures.app.data.db.dao

import androidx.annotation.VisibleForTesting
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Query
import com.kryptkode.footballfixtures.app.data.db.dao.base.BaseDao
import com.kryptkode.footballfixtures.app.data.models.squad.Squad

@Dao
abstract class  SquadDao : BaseDao<Squad>{
    @Query("SELECT * FROM `squad` WHERE teamId=:teamId")
    abstract fun getSquadForCompetition(teamId: Int?): DataSource.Factory<Int, Squad>

    @Query("SELECT * FROM `squad` WHERE teamId=:teamId")
    @VisibleForTesting
    abstract fun getSquadListForCompetition(teamId: Int?): List<Squad>

    @Query("DELETE FROM `squad`")
    abstract fun deleteAll()
}