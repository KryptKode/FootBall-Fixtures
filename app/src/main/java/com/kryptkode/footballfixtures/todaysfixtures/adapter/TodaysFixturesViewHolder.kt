package com.kryptkode.footballfixtures.todaysfixtures.adapter

import com.kryptkode.footballfixtures.R
import com.kryptkode.footballfixtures.app.base.recycler.BaseRecyclerViewHolder
import com.kryptkode.footballfixtures.app.data.models.score.Score
import com.kryptkode.footballfixtures.app.data.models.fixtures.Match
import com.kryptkode.footballfixtures.app.utils.Constants.REGULAR
import com.kryptkode.footballfixtures.app.utils.DateTimeUtils
import com.kryptkode.footballfixtures.databinding.ItemTodayBinding

class TodaysFixturesViewHolder(
    binding: ItemTodayBinding,
    private val listener: TodaysFixturesListener? = null
) :
    BaseRecyclerViewHolder<Match, ItemTodayBinding>(binding) {

    override fun performBind(item: Match?) {
        binding.tvTeamHome.text = item?.homeTeam?.name
        binding.tvTeamAway.text = item?.awayTeam?.name
        binding.tvStatus.text = item?.status
        binding.tvTime.text = DateTimeUtils.formatTime(item?.utcDate)
        binding.tvMd.text = binding.root.context.getString(R.string.md_text)
        binding.tvScore.text = binding.root.context.getString(R.string.score_text)
        binding.tvScoreAway.text = resolveAwayScore(item?.score)
        binding.tvScoreHome.text = resolveHomeScore(item?.score)



        binding.root.setOnClickListener {
            listener?.onItemClick(item)
        }
    }

    private fun resolveHomeScore(score: Score?): String? {
        return when (score?.duration) {
            REGULAR -> score.fullTimeScore?.homeTeamScore?.toString()
            else -> if (score?.penaltiesScore?.homeTeamScore != null) {
                score.penaltiesScore.homeTeamScore.toString()
            } else {
                score?.extraTimeScore?.homeTeamScore?.toString() ?: "0"
            }
        }
    }

    private fun resolveAwayScore(score: Score?): String? {
        return when (score?.duration) {
            REGULAR -> score.fullTimeScore?.awayTeamScore?.toString()
            else -> if (score?.penaltiesScore?.awayTeamScore != null) {
                score.penaltiesScore.awayTeamScore.toString()
            } else {
                score?.extraTimeScore?.awayTeamScore?.toString() ?: "0"
            }
        }
    }
}