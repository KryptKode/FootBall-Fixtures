package com.kryptkode.footballfixtures.competitions.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.kryptkode.footballfixtures.BR
import com.kryptkode.footballfixtures.R
import com.kryptkode.footballfixtures.app.base.activity.BaseViewModelActivity
import com.kryptkode.footballfixtures.app.data.models.competition.Competition
import com.kryptkode.footballfixtures.app.utils.Constants
import com.kryptkode.footballfixtures.databinding.ActivityCompetitonDetailBinding
import javax.inject.Inject

class CompetitionsDetailActivity :
    BaseViewModelActivity<ActivityCompetitonDetailBinding, CompetitionsDetailViewModel>() {

    @Inject
    lateinit var fragmentListProvider: FragmentsListProvider

    @Inject
    lateinit var fragmentTitleProvider: FragmentTitleProvider


    private val adapter = object :
        FragmentPagerAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getCount() = fragmentListProvider.list.size

        override fun getItem(position: Int): Fragment {
            val fragment = fragmentListProvider.list[position]
            fragment.arguments = getFragmentArguments()
            return fragment
        }

        override fun getPageTitle(position: Int) = fragmentTitleProvider.list[position]
    }

    private fun getFragmentArguments(): Bundle? {
        return intent?.getBundleExtra(Constants.EXTRAS)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        setSupportActionBar(binding.toolbar)
        showUpEnabled(true)
        val competition = getFragmentArguments()?.getParcelable<Competition>(Constants.EXTRAS)
        setActionBarTitle(competition?.name ?: getString(R.string.app_name))
        binding.viewPager.adapter = adapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)

    }


    override fun getLayoutResourceId() = R.layout.activity_competiton_detail

    override fun getBindingVariable() = BR._all

    override fun getViewModelClass() = CompetitionsDetailViewModel::class.java
}