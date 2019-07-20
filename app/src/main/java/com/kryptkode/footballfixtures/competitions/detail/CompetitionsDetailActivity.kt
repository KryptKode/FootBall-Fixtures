package com.kryptkode.footballfixtures.competitions.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.kryptkode.footballfixtures.BR
import com.kryptkode.footballfixtures.R
import com.kryptkode.footballfixtures.app.base.activity.BaseViewModelActivity
import com.kryptkode.footballfixtures.app.utils.Constants
import com.kryptkode.footballfixtures.competitions.detail.fixtures.FixturesFragment
import com.kryptkode.footballfixtures.databinding.ActivityCompetitonDetailBinding
import javax.inject.Inject
import javax.inject.Named

class CompetitionsDetailActivity :
    BaseViewModelActivity<ActivityCompetitonDetailBinding, CompetitionsDetailViewModel>() {

    @Inject
    lateinit var fragmentListProvider: FragmentsListProvider

    @Inject
    lateinit var fragmentTitleProvider: FragmentTitleProvider


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        showUpEnabled(true)
        val adapter = CompetitionDetailAdapter(fragmentListProvider.list, this)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = fragmentTitleProvider.list[position]
        }.attach()
    }


    override fun getLayoutResourceId() = R.layout.activity_competiton_detail

    override fun getBindingVariable() = BR._all

    override fun getViewModelClass() = CompetitionsDetailViewModel::class.java
}