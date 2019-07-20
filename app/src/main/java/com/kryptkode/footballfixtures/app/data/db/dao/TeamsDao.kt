package com.kryptkode.footballfixtures.app.data.db.dao

import androidx.annotation.VisibleForTesting
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Query
import com.kryptkode.footballfixtures.app.data.db.dao.base.BaseDao
import com.kryptkode.footballfixtures.app.data.models.team.Team

@Dao
abstract class  TeamsDao : BaseDao<Team>{
    @Query("SELECT * FROM `team` WHERE competitionId=:competitionId ORDER BY name ASC")
    abstract fun getTeamsForCompetition(competitionId: Int?): DataSource.Factory<Int, Team>

    @Query("SELECT * FROM `team` WHERE competitionId=:competitionId  ORDER BY name ASC")
    @VisibleForTesting
    abstract fun getTeamListForCompetition(competitionId: Int?): List<Team>

    @Query("DELETE FROM `team`")
    abstract fun deleteAll()
}