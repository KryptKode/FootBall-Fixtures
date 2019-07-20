package com.kryptkode.footballfixtures.competitions.detail.teams.detail

import com.kryptkode.footballfixtures.BR
import com.kryptkode.footballfixtures.R
import com.kryptkode.footballfixtures.app.base.fragment.BaseFragment
import com.kryptkode.footballfixtures.databinding.FragmentTeamDetailBinding
import javax.inject.Inject

class TeamDetailFragment @Inject constructor() :
    BaseFragment<FragmentTeamDetailBinding, TeamDetailViewModel>() {

    override fun getLayoutResourceId() = R.layout.fragment_team_detail

    override fun getBindingVariable() = BR._all

    override fun getViewModelClass() = TeamDetailViewModel::class.java
}