package com.kryptkode.footballfixtures.app.data.models.score

import com.google.gson.annotations.SerializedName

data class TeamScore(
    @SerializedName("homeTeam") val homeTeamScore: Int?,
    @SerializedName("awayTeam") val awayTeamScore: Int?
)