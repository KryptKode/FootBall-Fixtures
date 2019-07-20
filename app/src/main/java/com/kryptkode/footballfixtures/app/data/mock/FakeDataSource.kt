package com.kryptkode.footballfixtures.app.data.mock

import com.kryptkode.footballfixtures.app.data.models.competition.Competition
import com.kryptkode.footballfixtures.app.data.models.todays.Match
import com.kryptkode.footballfixtures.app.utils.DateTimeUtils
import java.util.*

object FakeDataSource {

    fun getFakeMatches(size: Int): MutableList<Match> {
        val list = mutableListOf<Match>()
        for (i in 0 until size) {
            list.add(
                Match(
                    i,
                    Competition(i, "Competition$i"),
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
}