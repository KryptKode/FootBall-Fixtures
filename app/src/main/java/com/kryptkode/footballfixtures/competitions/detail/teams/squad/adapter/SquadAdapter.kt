package com.kryptkode.footballfixtures.competitions.detail.teams.squad.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.kryptkode.footballfixtures.app.base.recycler.BaseRecyclerPagingAdapter
import com.kryptkode.footballfixtures.app.data.models.squad.Squad
import com.kryptkode.footballfixtures.databinding.ItemTeamDetailBinding


class SquadAdapter :
    BaseRecyclerPagingAdapter<Squad, SquadViewHolder>(Squad.diffUtil) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SquadViewHolder {
        return SquadViewHolder(
            ItemTeamDetailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

}
