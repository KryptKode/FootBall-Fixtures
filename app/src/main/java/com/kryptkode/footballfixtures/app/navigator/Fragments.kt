package com.kryptkode.footballfixtures.app.navigator

import com.kryptkode.footballfixtures.competitions.CompetitionsFragment
import com.kryptkode.footballfixtures.todaysfixtures.TodaysFixturesFragment


data class Fragments(
    val competitionsFragment: CompetitionsFragment,
    val todaysFixturesFragment: TodaysFixturesFragment
)