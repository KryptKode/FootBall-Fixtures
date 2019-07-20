package com.kryptkode.footballfixtures.competitions.detail.fixtures

import com.kryptkode.footballfixtures.BR
import com.kryptkode.footballfixtures.R
import com.kryptkode.footballfixtures.app.base.fragment.BaseFragment
import com.kryptkode.footballfixtures.databinding.FragmentFixturesBinding
import com.kryptkode.footballfixtures.todaysfixtures.TodaysFixturesViewModel
import javax.inject.Inject

class FixturesFragment @Inject constructor() : BaseFragment<FragmentFixturesBinding, FixturesViewModel>(){


    override fun getLayoutResourceId() = R.layout.fragment_fixtures

    override fun getBindingVariable() = BR._all

    override fun getViewModelClass() = FixturesViewModel::class.java
}