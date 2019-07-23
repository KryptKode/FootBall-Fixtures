package com.kryptkode.footballfixtures.app.data.db.dao

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kryptkode.footballfixtures.app.data.mock.FakeDbData
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertEquals
import kotlin.test.assertTrue


@RunWith(AndroidJUnit4::class)
class MatchDaoTest : BaseDaoTest<MatchDao>() {

    override fun initDao() = appDatabase?.matchDao()

    @Test
    fun should_Return_Empty_Matches_If_The_Table_Is_Empty() {
        val result = dao?.getMatchesList()
        assertTrue(result?.isEmpty() ?: false)
    }

    @Test
    fun onInsertingMatches_checkIf_RowCountIsCorrect(){
        val matches = FakeDbData.getFakeMatches(5)
        dao?.insert(matches)
        val count  = dao?.getMatchesList()?.size
        assertEquals(5, count, "The row count should be 5")
    }

}