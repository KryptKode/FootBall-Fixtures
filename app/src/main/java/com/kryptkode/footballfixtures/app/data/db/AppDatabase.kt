package com.kryptkode.footballfixtures.app.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kryptkode.footballfixtures.app.data.db.converter.*
import com.kryptkode.footballfixtures.app.data.models.competition.Competition
import com.kryptkode.footballfixtures.app.data.db.dao.CompetitionDao
import com.kryptkode.footballfixtures.app.data.db.dao.MatchDao
import com.kryptkode.footballfixtures.app.data.models.todays.Match

/**
 * Database schema that holds the list of repos.
 */
@Database(
    entities = [Competition::class, Match::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(
    DateConverter::class,
    CompetitionConverter::class,
    RefereeConverter::class,
    ScoreConverter::class,
    TeamConverter::class
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun competitionDao(): CompetitionDao

    abstract fun matchDao(): MatchDao
}
