package com.kryptkode.footballfixtures.app.data.db.dao

import com.kryptkode.footballfixtures.app.data.mock.FakeDbData
import org.junit.Test

class TeamsDaoTest : BaseDaoTest<TeamsDao>() {

    override fun initDao() = appDatabase?.teamsDao()

    @Test
    fun should_Return_Empty_Teams_If_The_Table_Is_Empty() {
        val result = dao?.getTeamListForCompetition(1)
        kotlin.test.assertTrue(result?.isEmpty() ?: false)
    }

    @Test
    fun onInsertingTeams_checkIf_RowCountIsCorrect() {
        val teams = FakeDbData.getFakeTeams(5)
        dao?.insert(teams)
        val count = dao?.getTeamListForCompetition(1)?.size
        kotlin.test.assertEquals(5, count, "The row count should be 5")
    }

}