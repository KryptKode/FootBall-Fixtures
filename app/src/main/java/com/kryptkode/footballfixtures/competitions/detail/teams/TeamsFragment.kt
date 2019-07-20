package com.kryptkode.footballfixtures.competitions.detail.teams

import com.kryptkode.footballfixtures.BR
import com.kryptkode.footballfixtures.R
import com.kryptkode.footballfixtures.app.base.fragment.BaseFragment
import com.kryptkode.footballfixtures.competitions.detail.fixtures.FixturesViewModel
import com.kryptkode.footballfixtures.databinding.FragmentTeamsBinding
import javax.inject.Inject

class TeamsFragment @Inject constructor() : BaseFragment<FragmentTeamsBinding, TeamsViewModel>() {

    override fun getLayoutResourceId() = R.layout.fragment_teams

    override fun getBindingVariable() = BR._all

    override fun getViewModelClass() = TeamsViewModel::class.java
}