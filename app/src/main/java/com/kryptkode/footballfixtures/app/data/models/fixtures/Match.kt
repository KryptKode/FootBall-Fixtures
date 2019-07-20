package com.kryptkode.footballfixtures.app.data.models.fixtures

import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.kryptkode.footballfixtures.app.data.models.competition.Competition
import com.kryptkode.footballfixtures.app.data.models.score.Score
import com.kryptkode.footballfixtures.app.data.models.fixtures.referee.Referee
import com.kryptkode.footballfixtures.app.data.models.team.Team
import com.kryptkode.footballfixtures.app.utils.DateTimeUtils
import java.util.*

@Entity
data class Match(
    @SerializedName("id") @PrimaryKey(autoGenerate = false) val id: Int,
    @SerializedName("competition") val competition: Competition?,
    @SerializedName("competitionId") var competitionId: Int?,
    @SerializedName("utcDate") val utcDate: String?,
    @SerializedName("status") val status: String?,
    @SerializedName("stage") val stage: String?,
    @SerializedName("score") val score: Score?,
    @SerializedName("homeTeam") val homeTeam: Team?,
    @SerializedName("awayTeam") val awayTeam: Team?,
    @SerializedName("referees") val referees: List<Referee>?,
    val date: Date? = DateTimeUtils.toDate(utcDate)
) {
    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Match>() {
            override fun areItemsTheSame(oldItem: Match, newItem: Match): Boolean {
                return newItem.id == oldItem.id
            }

            override fun areContentsTheSame(oldItem: Match, newItem: Match): Boolean {
                return newItem == oldItem
            }

        }
    }
}
