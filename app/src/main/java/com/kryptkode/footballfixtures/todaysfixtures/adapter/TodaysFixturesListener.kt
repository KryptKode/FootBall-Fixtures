package com.kryptkode.footballfixtures.todaysfixtures.adapter

import com.kryptkode.footballfixtures.app.data.models.todays.Match

interface TodaysFixturesListener {
    fun onItemClick(item:Match?)
}