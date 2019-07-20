package com.kryptkode.footballfixtures.competitions.detail.teams.adapter

import com.kryptkode.footballfixtures.app.data.models.team.Team

interface TeamListener {
    fun onItemClick(item:Team?)
}