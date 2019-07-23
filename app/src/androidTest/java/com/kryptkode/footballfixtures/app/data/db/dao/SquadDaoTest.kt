package com.kryptkode.footballfixtures.app.data.db.dao

import com.kryptkode.footballfixtures.app.data.mock.FakeDbData
import org.junit.Test

class SquadDaoTest : BaseDaoTest<SquadDao>() {

    override fun initDao() = appDatabase?.squadDao()

    @Test
    fun should_Return_Empty_Squad_If_The_Table_Is_Empty() {
        val result = dao?.getSquadListForTeam(1)
        kotlin.test.assertTrue(result?.isEmpty() ?: false)
    }

    @Test
    fun onInsertingSquad_checkIf_RowCountIsCorrect(){
        val squad = FakeDbData.getFakeSquad(5)
        dao?.insert(squad)
        val count  = dao?.getSquadListForTeam(1)?.size
        kotlin.test.assertEquals(5, count, "The row count should be 5")
    }

}