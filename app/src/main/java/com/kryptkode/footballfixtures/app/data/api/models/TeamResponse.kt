package com.kryptkode.footballfixtures.app.data.api.models

import com.google.gson.annotations.SerializedName
import com.kryptkode.footballfixtures.app.data.models.competition.Competition
import com.kryptkode.footballfixtures.app.data.models.table.Standings
import com.kryptkode.footballfixtures.app.data.models.team.Team

data class TeamResponse(
    @SerializedName("competition") val competition: Competition?,
    @SerializedName("teams") val teams: List<Team>?
)