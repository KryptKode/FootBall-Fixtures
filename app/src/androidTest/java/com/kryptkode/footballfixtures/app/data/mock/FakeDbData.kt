package com.kryptkode.footballfixtures.app.data.mock

import com.kryptkode.footballfixtures.app.data.models.competition.Competition
import com.kryptkode.footballfixtures.app.data.models.fixtures.Match
import com.kryptkode.footballfixtures.app.data.models.squad.Squad
import com.kryptkode.footballfixtures.app.data.models.table.Table
import com.kryptkode.footballfixtures.app.data.models.team.Team
import com.kryptkode.footballfixtures.app.utils.DateTimeUtils
import java.util.*

object FakeDbData {
    fun getFakeMatches(size: Int): MutableList<Match> {
        val list = mutableListOf<Match>()
        for (i in 0 until size) {
            list.add(
                Match(
                    i,
                    Competition(i, "Competition$i"),
                    0,
                    DateTimeUtils.formatDate(Date()),
                    "Status $i",
                    "",
                    null,
                    null,
                    null,
                    null
                )
            )
        }

        return list
    }

    fun getFakeMatch(): Match {
        return Match(
            1,
            Competition(1, "Competition$1"),
            0,
            DateTimeUtils.formatDate(Date()),
            "Status $1",
            "",
            null,
            null,
            null,
            null
        )
    }

    fun getCompetitions(size: Int): MutableList<Competition> {
        val list = mutableListOf<Competition>()
        for (i in 0 until size) {
            list.add(
                Competition(i, "Competition$i")
            )
        }

        return list
    }

    fun getFakeCompetition(): Competition {
        return Competition(1, "Competition$1")
    }

    fun getFakeSquad(size: Int): MutableList<Squad> {
        val list = mutableListOf<Squad>()
        for (i in 0 until size) {
            list.add(
                Squad(
                    i,
                    "Status $i",
                    "Position $i",
                    1
                )
            )
        }

        return list
    }

    fun getFakeTable(size: Int, competitionId:Int): MutableList<Table> {
        val list = mutableListOf<Table>()
        for (i in 1 .. size) {
            list.add(
                Table(
                    competitionId,
                    i,
                    i,
                    Team(competitionId, i, "", "", "", ""),
                    i, i, i, i, i, i, i, i
                )
            )
        }

        return list
    }

    fun getFakeTeams(size: Int): MutableList<Team> {
        val list = mutableListOf<Team>()
        for (i in 0 until size) {
            list.add(
                Team(1, i, "", "", "", "")
            )
        }

        return list
    }

    fun getFakeTeam(): Team{
        return Team(1, 1, "Hello WOrld", "", "", "")
    }
}