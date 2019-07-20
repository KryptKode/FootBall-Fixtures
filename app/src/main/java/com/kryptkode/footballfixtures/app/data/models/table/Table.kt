package com.kryptkode.footballfixtures.app.data.models.table

import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.kryptkode.footballfixtures.app.data.models.team.Team

@Entity
data class Table(
    @SerializedName("competitionId") var competitionId: Int?,
    @SerializedName("position") @PrimaryKey(autoGenerate = false) val position: Int?,
    @SerializedName("team") val team: Team,
    @SerializedName("playedGames") val playedGames: Int?,
    @SerializedName("won") val won: Int?,
    @SerializedName("draw") val draw: Int?,
    @SerializedName("lost") val lost: Int?,
    @SerializedName("points") val points: Int?,
    @SerializedName("goalsFor") val goalsFor: Int?,
    @SerializedName("goalsAgainst") val goalsAgainst: Int?,
    @SerializedName("goalDifference") val goalDifference: Int?
) {
    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Table>() {
            override fun areItemsTheSame(oldItem: Table, newItem: Table): Boolean {
                return newItem.position == oldItem.position
            }

            override fun areContentsTheSame(oldItem: Table, newItem: Table): Boolean {
                return newItem == oldItem
            }

        }
    }
}