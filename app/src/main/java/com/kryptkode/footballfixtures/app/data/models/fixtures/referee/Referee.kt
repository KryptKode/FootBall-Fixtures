package com.kryptkode.footballfixtures.app.data.models.fixtures.referee

import com.google.gson.annotations.SerializedName

data class Referee(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("nationality") val nationality: String?
)