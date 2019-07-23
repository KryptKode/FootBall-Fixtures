package com.kryptkode.footballfixtures.app.data.api.models

import com.google.gson.annotations.SerializedName
import com.kryptkode.footballfixtures.app.data.models.fixtures.Match

data class TodaysFixturesResponse(@SerializedName("matches") val matches: List<Match>)