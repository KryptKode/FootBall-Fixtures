package com.kryptkode.footballfixtures.app.data.models.score

import com.google.gson.annotations.SerializedName

data class Score(@SerializedName("winner") val winner:String?,
                 @SerializedName("duration") val duration:String?,
                 @SerializedName("fullTime") val fullTimeScore:TeamScore?,
                 @SerializedName("halfTime") val halfTimeScore:TeamScore?,
                 @SerializedName("extraTime") val extraTimeScore:TeamScore?,
                 @SerializedName("penalties") val penaltiesScore:TeamScore?)