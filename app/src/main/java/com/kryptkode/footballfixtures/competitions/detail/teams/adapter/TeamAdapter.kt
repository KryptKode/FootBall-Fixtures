package com.kryptkode.footballfixtures.competitions.detail.teams.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.kryptkode.footballfixtures.app.base.recycler.BaseRecyclerPagingAdapter
import com.kryptkode.footballfixtures.app.data.models.team.Team
import com.kryptkode.footballfixtures.databinding.ItemTeamsBinding

class TeamAdapter(private val listener: TeamListener) :
    BaseRecyclerPagingAdapter<Team, TeamViewHolder>(Team.diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        return TeamViewHolder(
            ItemTeamsBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            listener
        )
    }

}