package com.kryptkode.footballfixtures.app.data.mock

import com.google.gson.Gson
import com.kryptkode.footballfixtures.app.data.api.models.CompetitionResponse
import com.kryptkode.footballfixtures.app.data.models.competition.Competition
import io.reactivex.Observable
import java.io.File

object MockDataSource {

    val competitonData by lazy {
        Observable.just(getCompetitionData())
    }

    fun getCompetitionData(): CompetitionResponse {
        val data = getJson("competitions.json")
        return Gson().fromJson(data, CompetitionResponse::class.java)
    }

    fun getJson(path: String): String {
        val uri = this.javaClass.classLoader?.getResource(path)
        val file = File(uri?.path ?: "")
        return String(file.readBytes())
    }
}

