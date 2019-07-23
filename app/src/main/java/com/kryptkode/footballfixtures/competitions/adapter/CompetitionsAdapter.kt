package com.kryptkode.footballfixtures.competitions.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.kryptkode.footballfixtures.app.base.recycler.BaseRecyclerPagingAdapter
import com.kryptkode.footballfixtures.app.data.models.competition.Competition
import com.kryptkode.footballfixtures.databinding.ItemCompetitionBinding

class CompetitionsAdapter(private val listener: CompetitionsListener? = null) :
    BaseRecyclerPagingAdapter<Competition, CompetitionsViewHolder>(Competition.diffUtilItemCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompetitionsViewHolder {
        return CompetitionsViewHolder(
            ItemCompetitionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), listener
        )
    }
}