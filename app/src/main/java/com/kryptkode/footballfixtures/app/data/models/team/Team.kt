package com.kryptkode.footballfixtures.app.data.models.team

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Team(
    @SerializedName("id") @PrimaryKey(autoGenerate = false) val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("crestUrl") val crestUrl: String?,
    @SerializedName("shortName") val shortName: String?,
    @SerializedName("tla") val tla: String?
)