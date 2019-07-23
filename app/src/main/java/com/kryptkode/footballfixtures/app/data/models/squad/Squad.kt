package com.kryptkode.footballfixtures.app.data.models.squad

import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Squad(
    @SerializedName("id") @PrimaryKey(autoGenerate = false) val id: Int,
    @SerializedName("name") val name: String?,
    @SerializedName("position") val position: String?,
    @SerializedName("teamId") var teamId: Int?
) {
    @Ignore
    var type: Int = 0

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Squad>() {
            override fun areItemsTheSame(oldItem: Squad, newItem: Squad): Boolean {
                return newItem.id == oldItem.id
            }

            override fun areContentsTheSame(oldItem: Squad, newItem: Squad): Boolean {
                return newItem == oldItem
            }

        }
    }
}