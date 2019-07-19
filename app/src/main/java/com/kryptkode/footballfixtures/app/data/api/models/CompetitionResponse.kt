package com.kryptkode.footballfixtures.app.data.api.models

import com.google.gson.annotations.SerializedName
import com.kryptkode.footballfixtures.app.data.models.competition.Competition

data class CompetitionResponse(@SerializedName("count") val count:Int?,
                               @SerializedName("competitions") val competitions: List<Competition>?)