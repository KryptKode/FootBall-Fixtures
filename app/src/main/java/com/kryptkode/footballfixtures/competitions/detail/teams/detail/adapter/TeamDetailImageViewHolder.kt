package com.kryptkode.footballfixtures.competitions.detail.teams.detail.adapter

import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.kryptkode.footballfixtures.R
import com.kryptkode.footballfixtures.app.base.recycler.BaseRecyclerViewHolder
import com.kryptkode.footballfixtures.app.data.models.squad.Squad
import com.kryptkode.footballfixtures.app.utils.AttrUtils
import com.kryptkode.footballfixtures.app.views.PlaceHolderDrawable
import com.kryptkode.footballfixtures.databinding.ItemTeamImageBinding
import com.kryptkode.imageloader.ImageLoader
import timber.log.Timber

class TeamDetailImageViewHolder(
    binding: ItemTeamImageBinding
) :
    BaseRecyclerViewHolder<Squad, ItemTeamImageBinding>(binding) {

    private val placeholder = PlaceHolderDrawable(binding.root.context)
    private val errorDrawable = ContextCompat.getDrawable(
        binding.root.context,
        R.drawable.ic_soccer
    ).apply {
        DrawableCompat.setTint(
            this ?: return@apply,
            AttrUtils.convertAttrToColor(R.attr.colorError, binding.root.context)
        )
    }

    override fun performBind(item: Squad?) {
        Timber.d("URL: ${item?.name}")
        ImageLoader()
            .with(binding.imgTeamLogo)
            .placeholder(placeholder)
            .load(item?.name)
            .error(errorDrawable)
            .begin()
    }
}