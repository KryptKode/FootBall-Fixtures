package com.kryptkode.footballfixtures.competitions.detail.teams.detail

import android.os.Bundle
import com.kryptkode.footballfixtures.app.base.activity.BaseFragmentActivity
import javax.inject.Inject
import com.r0adkll.slidr.Slidr
import com.kryptkode.footballfixtures.R
import com.kryptkode.footballfixtures.app.utils.AttrUtils
import com.kryptkode.footballfixtures.app.utils.Constants
import com.kryptkode.footballfixtures.app.utils.SizeUtils
import com.r0adkll.slidr.model.SlidrConfig
import com.r0adkll.slidr.model.SlidrPosition


class TeamDetailActivity : BaseFragmentActivity<TeamDetailFragment>() {

    @Inject
    lateinit var teamDetailFragment: TeamDetailFragment

    override val fragment: TeamDetailFragment
        get() = bindArgsToFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val config = SlidrConfig.Builder()
            .primaryColor(AttrUtils.convertAttrToColor(R.attr.colorPrimary, this))
            .secondaryColor(AttrUtils.convertAttrToColor(R.attr.colorPrimary, this))
            .position(SlidrPosition.TOP)
            .velocityThreshold(2400f)
            .distanceThreshold(.25f)
            .edge(true)
            .touchSize(SizeUtils.dp2px(this, 32f).toFloat()).build()

        Slidr.attach(this, config)
    }

    private fun bindArgsToFragment():TeamDetailFragment{
        teamDetailFragment.arguments = intent?.getBundleExtra(Constants.EXTRAS)
        return  teamDetailFragment
    }
}