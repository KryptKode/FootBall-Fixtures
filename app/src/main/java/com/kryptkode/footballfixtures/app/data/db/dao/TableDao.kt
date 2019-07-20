package com.kryptkode.footballfixtures.app.data.db.dao

import androidx.annotation.VisibleForTesting
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Query
import com.kryptkode.footballfixtures.app.data.db.dao.base.BaseDao
import com.kryptkode.footballfixtures.app.data.models.table.Table

@Dao
abstract class TableDao : BaseDao<Table> {
    @Query("SELECT * FROM `table` WHERE competitionId=:competitionId ORDER BY position ASC")
    abstract fun getTableForCompetition(competitionId: Int?): DataSource.Factory<Int, Table>

    @Query("SELECT * FROM `table` WHERE competitionId=:competitionId  ORDER BY position ASC")
    @VisibleForTesting
    abstract fun getTableListForCompetition(competitionId: Int?): List<Table>

    @Query("DELETE FROM `table`")
    abstract fun deleteAll()
}