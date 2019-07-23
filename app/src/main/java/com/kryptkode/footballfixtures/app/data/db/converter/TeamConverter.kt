package com.kryptkode.footballfixtures.app.data.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.kryptkode.footballfixtures.app.data.models.team.Team

class TeamConverter {
    companion object {
        /**
         * Convert a string value to a Team
         * @param value the string value
         * @return the Team
         */
        @TypeConverter
        @JvmStatic
        fun fromStringToTeam(value: String?): Team? {
            return if (value == null) null else Gson().fromJson(value, Team::class.java)
        }

        /**
         * Convert a @see Team to a string value
         * @param score the Team
         * @return the String value
         */
        @TypeConverter
        @JvmStatic
        fun fromTeamToString(score: Team?): String? {
            return Gson().toJson(score)
        }
    }
}