package com.kryptkode.footballfixtures.app.data.db.dao

import android.util.Log
import com.kryptkode.footballfixtures.app.data.mock.FakeDataSource
import org.junit.Assert.*
import org.junit.Test
import kotlin.test.assertEquals

class TableDaoTest : BaseDaoTest<TableDao>() {

    override fun initDao() = appDatabase?.tableDao()

    @Test
    fun should_Return_Empty_Table_If_The_Table_Is_Empty() {
        val result = dao?.getTableListForCompetition(1)
        assertTrue(result?.isEmpty() ?: false)
    }

    @Test
    fun onInsertingTable_checkIf_RowCountIsCorrect() {
        val table = FakeDataSource.getFakeTable(5, 1)

        dao?.insert(table)

        val count  = dao?.getTableListForCompetition(1)?.size
        assertEquals(5, count, "The row count should be 5")
    }

    @Test
    fun should_Return_No_Table_Data_After_Deleting_All_Table_Data() {
        val table = FakeDataSource.getFakeTable(5, 1)

        dao?.insert(table)

        val count = dao?.getTableListForCompetition(1)?.size
        assertEquals(5, count, "The row count should be 5")

        dao?.deleteAll(1)

        val result = dao?.getTableListForCompetition(1)
        assertTrue(result?.isEmpty() ?: false)
    }

}