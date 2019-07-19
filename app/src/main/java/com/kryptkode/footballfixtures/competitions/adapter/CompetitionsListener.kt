package com.kryptkode.footballfixtures.competitions.adapter

import com.kryptkode.footballfixtures.app.data.models.competition.Competition

interface CompetitionsListener {
    fun onItemClick(competition: Competition?)
}