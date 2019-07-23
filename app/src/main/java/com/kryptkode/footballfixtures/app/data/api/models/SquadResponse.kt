package com.kryptkode.footballfixtures.app.data.api.models

import com.google.gson.annotations.SerializedName
import com.kryptkode.footballfixtures.app.data.models.squad.Squad

data class SquadResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("squad") val squad: List<Squad>?
)