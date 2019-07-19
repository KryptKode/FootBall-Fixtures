package com.kryptkode.footballfixtures.app.data.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kryptkode.footballfixtures.app.data.models.todays.referee.Referee

import java.util.*

class RefereeConverter {
    companion object {
        /**
         * Convert a string value to a Referee
         * @param value the string value
         * @return the Referee
         */
        @TypeConverter
        @JvmStatic
        fun fromStringToReferee(value: String?): Referee? {
            return if (value == null) null else Gson().fromJson(value, Referee::class.java)
        }

        /**
         * Convert a @see Referee to a string value
         * @param score the Referee
         * @return the String value
         */
        @TypeConverter
        @JvmStatic
        fun fromRefereeToString(score: Referee?): String? {
            return Gson().toJson(score)
        }


        /**
         * Convert a string value to a Referee
         * @param value the string value
         * @return the Referee
         */
        @TypeConverter
        @JvmStatic
        fun fromStringToRefereeList(value: String?): List<Referee>? {
            return if (value == null) null else Gson().fromJson(value, object: TypeToken<List<Referee>> () {}.type)
        }

        /**
         * Convert a @see Referee to a string value
         * @param score the Referee
         * @return the String value
         */
        @TypeConverter
        @JvmStatic
        fun fromRefereeListToString(score: List<Referee>?): String? {
            return Gson().toJson(score)
        }
    }
}