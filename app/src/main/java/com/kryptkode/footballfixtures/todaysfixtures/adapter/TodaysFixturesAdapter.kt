package com.kryptkode.footballfixtures.todaysfixtures.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.kryptkode.footballfixtures.app.base.recycler.BaseRecyclerPagingAdapter
import com.kryptkode.footballfixtures.app.data.models.fixtures.Match
import com.kryptkode.footballfixtures.competitions.detail.fixtures.adapter.FixturesViewHolder
import com.kryptkode.footballfixtures.databinding.ItemTodayBinding

class TodaysFixturesAdapter(private val listener: TodaysFixturesListener? = null) :
    BaseRecyclerPagingAdapter<Match, FixturesViewHolder>(Match.diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FixturesViewHolder {
        return FixturesViewHolder(
            ItemTodayBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), listener
        )
    }
}