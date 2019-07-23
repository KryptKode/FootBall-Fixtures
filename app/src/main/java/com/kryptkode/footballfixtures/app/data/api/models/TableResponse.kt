package com.kryptkode.footballfixtures.app.data.api.models

import com.google.gson.annotations.SerializedName
import com.kryptkode.footballfixtures.app.data.models.competition.Competition
import com.kryptkode.footballfixtures.app.data.models.table.Standings

data class TableResponse(
    @SerializedName("competition") val competition: Competition,
    @SerializedName("standings") val standings: List<Standings>?
)