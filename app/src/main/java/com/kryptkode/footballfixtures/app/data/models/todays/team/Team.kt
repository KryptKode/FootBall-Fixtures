package com.kryptkode.footballfixtures.app.data.models.todays.team

import com.google.gson.annotations.SerializedName

data class Team(@SerializedName("id") val id:Int?,
                @SerializedName("name") val name:String?)