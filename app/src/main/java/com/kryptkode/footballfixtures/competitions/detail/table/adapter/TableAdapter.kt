package com.kryptkode.footballfixtures.competitions.detail.table.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.kryptkode.footballfixtures.app.base.recycler.BaseRecyclerPagingAdapter
import com.kryptkode.footballfixtures.app.data.models.table.Table
import com.kryptkode.footballfixtures.databinding.ItemTableBinding

class TableAdapter : BaseRecyclerPagingAdapter<Table, TableViewHolder>(Table.diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TableViewHolder {
        return TableViewHolder(
            ItemTableBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}