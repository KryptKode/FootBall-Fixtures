package com.kryptkode.footballfixtures.app.data.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.kryptkode.footballfixtures.app.data.models.competition.Competition

import java.util.*

class CompetitionConverter {
    companion object {
        /**
         * Convert a string value to a Competition
         * @param value the string value
         * @return the Competition
         */
        @TypeConverter
        @JvmStatic
        fun fromCompetitionToString(value: String?): Competition? {
            return if (value == null) null else Gson().fromJson(value, Competition::class.java)
        }

        /**
         * Convert a @see Competition to a string value
         * @param score the Competition
         * @return the String value
         */
        @TypeConverter
        @JvmStatic
        fun fromStringToCompetition(score: Competition?): String? {
            return Gson().toJson(score)
        }
    }
}