package com.kryptkode.footballfixtures.competitions.detail.teams.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.kryptkode.footballfixtures.app.base.recycler.BaseRecyclerPagingAdapter
import com.kryptkode.footballfixtures.app.data.models.squad.Squad
import com.kryptkode.footballfixtures.databinding.ItemTeamDetailBinding


class TeamDetailAdapter :
    BaseRecyclerPagingAdapter<Squad, TeamDetailViewHolder>(Squad.diffUtil) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TeamDetailViewHolder {
        return TeamDetailViewHolder(
            ItemTeamDetailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

}
