package com.kryptkode.footballfixtures.competitions.detail.teams.adapter

import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.kryptkode.footballfixtures.R
import com.kryptkode.footballfixtures.app.base.recycler.BaseRecyclerViewHolder
import com.kryptkode.footballfixtures.app.data.models.team.Team
import com.kryptkode.footballfixtures.app.utils.AttrUtils
import com.kryptkode.footballfixtures.app.views.PlaceHolderDrawable
import com.kryptkode.footballfixtures.databinding.ItemTeamsBinding
import com.kryptkode.imageloader.ImageLoader
import timber.log.Timber

class TeamViewHolder(
    binding: ItemTeamsBinding,
    private val listener: TeamListener? = null
) :
    BaseRecyclerViewHolder<Team, ItemTeamsBinding>(binding) {

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

    override fun performBind(item: Team?) {
        binding.tvTeamName.text = item?.name
        Timber.d("URL: ${item?.crestUrl}")
        ImageLoader()
            .with(binding.imgTeamLogo)
            .placeholder(placeholder)
            .load(item?.crestUrl)
            .error(errorDrawable)
            .begin()

        binding.root.setOnClickListener {
            listener?.onItemClick(item)
        }
    }
}