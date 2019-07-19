package com.kryptkode.footballfixtures.app.data.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.kryptkode.footballfixtures.app.data.models.score.Score
import java.util.*

class ScoreConverter {
    companion object {
        /**
         * Convert a string value to a Score
         * @param value the string value
         * @return the Score
         */
        @TypeConverter
        @JvmStatic
        fun fromStringToScore(value: String?): Score? {
            return if (value == null) null else Gson().fromJson(value, Score::class.java)
        }

        /**
         * Convert a @see Score to a string value
         * @param score the Score
         * @return the String value
         */
        @TypeConverter
        @JvmStatic
        fun fromScoreToString(score: Score?): String? {
            return Gson().toJson(score)
        }
    }
}