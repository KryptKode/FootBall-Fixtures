package com.kryptkode.footballfixtures.todaysfixtures.adapter

import com.kryptkode.footballfixtures.app.data.models.fixtures.Match

interface TodaysFixturesListener {
    fun onItemClick(item:Match?)
}