package com.kryptkode.footballfixtures.app.data.models.competition

import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Competition(
    @SerializedName("id") @PrimaryKey(autoGenerate = false) val id: Int,
    @SerializedName("name") val name: String?
) {
    companion object {
        val diffUtilItemCallback = object : DiffUtil.ItemCallback<Competition>() {
            override fun areItemsTheSame(oldItem: Competition, newItem: Competition): Boolean {
                return newItem.id == oldItem.id
            }

            override fun areContentsTheSame(oldItem: Competition, newItem: Competition): Boolean {
                return newItem == oldItem
            }

        }
    }
}