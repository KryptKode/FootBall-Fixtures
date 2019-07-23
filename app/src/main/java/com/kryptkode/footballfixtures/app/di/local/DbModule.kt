package com.kryptkode.footballfixtures.app.di.local


import android.content.Context
import androidx.room.Room
import com.kryptkode.footballfixtures.app.data.db.AppDatabase
import com.kryptkode.footballfixtures.app.data.db.AppDbManager
import com.kryptkode.footballfixtures.app.data.db.DbManager
import com.kryptkode.footballfixtures.app.di.app.scopes.AppScope
import com.kryptkode.footballfixtures.app.utils.Constants
import com.kryptkode.footballfixtures.app.utils.schedulers.AppSchedulers
import dagger.Module
import dagger.Provides

@Module
open class DbModule {

    @Provides
    @AppScope
   open fun provideAppDatabase(context: Context): AppDatabase {
        val builder =
            Room.databaseBuilder(context, AppDatabase::class.java, Constants.DATABASE_NAME)
        return builder.build()
    }

    @Provides
    @AppScope
    fun provideDatabaseManager(
        schedulers: AppSchedulers,
        appDatabase: AppDatabase
    ): AppDbManager {
        return AppDbManager(schedulers, appDatabase)
    }


    @Provides
    @AppScope
    fun provideDbManager(schedulers: AppSchedulers, appDatabase: AppDatabase):DbManager{
        return AppDbManager(schedulers, appDatabase)
    }
}