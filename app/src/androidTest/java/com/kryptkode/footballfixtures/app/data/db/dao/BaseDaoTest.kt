package com.kryptkode.footballfixtures.app.data.db.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.kryptkode.footballfixtures.app.data.db.AppDatabase
import org.junit.After
import org.junit.Before
import org.junit.Rule

abstract class BaseDaoTest<T> {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    protected var appDatabase: AppDatabase? = null

    protected var dao: T? = null


    @Before
    fun initDb() {
        appDatabase = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            AppDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        dao = initDao()
    }

    abstract fun initDao(): T?

    @After
    fun cleanUp() {
        appDatabase?.close()
    }
}