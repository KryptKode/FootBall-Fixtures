package com.kryptkode.footballfixtures.app.base.recycler

import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil

abstract class BaseRecyclerPagingAdapter<T, V>(diffCallback: DiffUtil.ItemCallback<T>) :
    PagedListAdapter<T, V>(diffCallback) where V: BaseRecyclerViewHolder<T, *> {

    override fun onBindViewHolder(holder: V, position: Int) {
        holder.performBind(getItem(position))
    }
}