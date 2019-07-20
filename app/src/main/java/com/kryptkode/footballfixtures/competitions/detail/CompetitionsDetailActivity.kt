package com.kryptkode.footballfixtures.competitions.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.kryptkode.footballfixtures.BR
import com.kryptkode.footballfixtures.R
import com.kryptkode.footballfixtures.app.base.activity.BaseViewModelActivity
import com.kryptkode.footballfixtures.app.data.models.competition.Competition
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


    private val adapter = object : FragmentStateAdapter(this) {
        override fun getItemCount() = fragmentListProvider.list.size

        override fun createFragment(position: Int): Fragment {
            val fragment = fragmentListProvider.list[position]
            fragment.arguments = getFragmentArguments()
            return fragment
        }
    }

    private fun getFragmentArguments(): Bundle? {
        return intent?.getBundleExtra(Constants.EXTRAS)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        showUpEnabled(true)
        val competition = getFragmentArguments()?.getParcelable<Competition>(Constants.EXTRAS)
        setActionBarTitle(competition?.name ?: getString(R.string.app_name))
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = fragmentTitleProvider.list[position]
        }.attach()
    }


    override fun getLayoutResourceId() = R.layout.activity_competiton_detail

    override fun getBindingVariable() = BR._all

    override fun getViewModelClass() = CompetitionsDetailViewModel::class.java
}