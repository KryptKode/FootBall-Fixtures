package com.kryptkode.footballfixtures.app.data.models.team

import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Team(
    @SerializedName("competitionId") var competitionId: Int?,
    @SerializedName("id") @PrimaryKey(autoGenerate = false) val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("crestUrl") val crestUrl: String?,
    @SerializedName("shortName") val shortName: String?,
    @SerializedName("tla") val tla: String?
){
    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Team>() {
            override fun areItemsTheSame(oldItem: Team, newItem: Team): Boolean {
                return newItem.id == oldItem.id
            }

            override fun areContentsTheSame(oldItem: Team, newItem: Team): Boolean {
                return newItem == oldItem
            }

        }
    }
}