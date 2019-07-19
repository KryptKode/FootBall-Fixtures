package com.kryptkode.footballfixtures.app.data.db.converter

import androidx.room.TypeConverter
import java.util.*

class DateConverter{

    companion object {
        /**
         * Convert a long value to a date
         * @param value the long value
         * @return the date
         */
        @TypeConverter
        @JvmStatic
        fun fromTimestamp(value: Long?): Date? {
            return if (value == null) null else Date(value)
        }

        /**
         * Convert a date to a long value
         * @param date the date
         * @return the long value
         */
        @TypeConverter
        @JvmStatic
        fun dateToTimestamp(date: Date?): Long? {
            return date?.time
        }
    }

}
