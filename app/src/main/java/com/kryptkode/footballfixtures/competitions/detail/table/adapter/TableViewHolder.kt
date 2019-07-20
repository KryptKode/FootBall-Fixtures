package com.kryptkode.footballfixtures.competitions.detail.table.adapter

import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.kryptkode.footballfixtures.R
import com.kryptkode.footballfixtures.app.base.recycler.BaseRecyclerViewHolder
import com.kryptkode.footballfixtures.app.data.models.table.Table
import com.kryptkode.footballfixtures.app.utils.AttrUtils
import com.kryptkode.footballfixtures.app.views.PlaceHolderDrawable
import com.kryptkode.footballfixtures.databinding.ItemTableBinding
import com.kryptkode.imageloader.ImageLoader

class TableViewHolder(binding: ItemTableBinding) :
    BaseRecyclerViewHolder<Table, ItemTableBinding>(binding) {

    private val placeholder = PlaceHolderDrawable(binding.root.context)
    private val errorDrawable = ContextCompat.getDrawable(
        binding.root.context,
        R.drawable.ic_soccer
    ).apply {
        DrawableCompat.setTint(
            this ?: return@apply,
            AttrUtils.convertAttrToColor(R.attr.colorAccent, binding.root.context)
        )
    }

    override fun performBind(item: Table?) {
        binding.tvNumber.text = item?.position.toString()
        binding.tvTeamName.text = item?.team?.name
        binding.tvGoals.text = item?.goalDifference.toString()
        binding.tvGamesPlayed.text = item?.playedGames.toString()
        binding.tvPoints.text = item?.points.toString()

        ImageLoader()
            .with(binding.imgTeamLogo)
            .placeholder(placeholder)
            .load(item?.team?.crestUrl)
            .error(errorDrawable)
            .begin()
    }
}