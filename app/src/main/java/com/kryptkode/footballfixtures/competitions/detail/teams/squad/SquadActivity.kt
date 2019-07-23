package com.kryptkode.footballfixtures.competitions.detail.teams.squad

import android.os.Bundle
import com.kryptkode.footballfixtures.R
import com.kryptkode.footballfixtures.app.base.activity.BaseFragmentActivity
import com.kryptkode.footballfixtures.app.data.models.team.Team
import com.kryptkode.footballfixtures.app.utils.AttrUtils
import com.kryptkode.footballfixtures.app.utils.Constants
import com.kryptkode.footballfixtures.app.utils.SizeUtils
import com.r0adkll.slidr.Slidr
import com.r0adkll.slidr.model.SlidrConfig
import com.r0adkll.slidr.model.SlidrPosition
import javax.inject.Inject


class SquadActivity : BaseFragmentActivity<SquadFragment>() {

    companion object{
        fun getBundleExtra(team: Team): Bundle {
            val data = Bundle()
            data.putParcelable(Constants.EXTRAS, team)
            return data
        }
    }

    @Inject
    lateinit var squadFragment: SquadFragment

    override val fragment: SquadFragment
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

    private fun bindArgsToFragment():SquadFragment{
        squadFragment.arguments = intent?.getBundleExtra(Constants.EXTRAS)
        return  squadFragment
    }
}