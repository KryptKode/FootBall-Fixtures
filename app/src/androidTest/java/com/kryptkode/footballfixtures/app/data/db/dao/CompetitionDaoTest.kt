package com.kryptkode.footballfixtures.app.data.db.dao

import com.kryptkode.footballfixtures.app.data.mock.FakeDbData
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.test.assertEquals

class CompetitionDaoTest : BaseDaoTest<CompetitionDao>(){
    override fun initDao() = appDatabase?.competitionDao()

    @Test
    fun should_Return_Empty_Competition_If_The_Table_Is_Empty() {
        val result = dao?.getCompetitionsList()
        assertTrue(result?.isEmpty() ?: false)
    }

    @Test
    fun onInsertingCompetitions_checkIf_RowCountIsCorrect(){
        val competitions = FakeDbData.getCompetitions(5)
        dao?.insert(competitions)
        val count  = dao?.getCompetitionsList()?.size
        assertEquals(5, count, "The row count should be 5")
    }
}