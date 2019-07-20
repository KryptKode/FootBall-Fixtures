package com.kryptkode.footballfixtures.competitions.detail.teams.detail.adapter

import com.kryptkode.footballfixtures.app.base.recycler.BaseRecyclerViewHolder
import com.kryptkode.footballfixtures.app.data.models.squad.Squad
import com.kryptkode.footballfixtures.databinding.ItemTeamDetailBinding

class TeamDetailViewHolder(binding: ItemTeamDetailBinding) :
    BaseRecyclerViewHolder<Squad, ItemTeamDetailBinding>(binding) {

    override fun performBind(item: Squad?) {
        binding.tvNumber.text = (adapterPosition + 1).toString()
        binding.tvTeamName.text = item?.name
        binding.tvPosition.text = item?.position
    }
}