package com.kryptkode.footballfixtures.competitions.adapter

import com.kryptkode.footballfixtures.app.base.recycler.BaseRecyclerViewHolder
import com.kryptkode.footballfixtures.app.data.models.competition.Competition
import com.kryptkode.footballfixtures.databinding.ItemCompetitionBinding

class CompetitionsViewHolder(
    binding: ItemCompetitionBinding,
    private val listener: CompetitionsListener? = null
) :
    BaseRecyclerViewHolder<Competition, ItemCompetitionBinding>(binding) {

    override fun performBind(item: Competition?) {
        binding.tvName.text = item?.name
        binding.root.setOnClickListener {
            listener?.onItemClick(item)
        }
    }
}