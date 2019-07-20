package com.kryptkode.footballfixtures.competitions.detail.teams.detail

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.kryptkode.footballfixtures.BR
import com.kryptkode.footballfixtures.R
import com.kryptkode.footballfixtures.app.base.fragment.BaseFragment
import com.kryptkode.footballfixtures.app.data.models.team.Team
import com.kryptkode.footballfixtures.app.utils.AttrUtils
import com.kryptkode.footballfixtures.app.utils.Constants
import com.kryptkode.footballfixtures.app.utils.NetworkState
import com.kryptkode.footballfixtures.app.utils.Status
import com.kryptkode.footballfixtures.app.views.ItemDivider
import com.kryptkode.footballfixtures.app.views.PlaceHolderDrawable
import com.kryptkode.footballfixtures.competitions.detail.CompetitionsDetailActivity
import com.kryptkode.footballfixtures.competitions.detail.teams.detail.adapter.TeamDetailAdapter
import com.kryptkode.footballfixtures.databinding.FragmentTeamDetailBinding
import com.kryptkode.imageloader.ImageLoader
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class TeamDetailFragment @Inject constructor() :
    BaseFragment<FragmentTeamDetailBinding, TeamDetailViewModel>(), HasAndroidInjector {

    private val adapter = TeamDetailAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
        loadData()
        //load data
    }

    private fun initObservers() {
        viewModel.networkState.observe(this, Observer {
            binding.swipeRefreshLayout.isRefreshing = it == NetworkState.LOADING
            binding.emptyLayout.tvMessage.text = if (it == NetworkState.LOADING) {
                getString(R.string.loading)
            } else {
                getString(R.string.no_team_msg)
            }


            if (it.status == Status.FAILED) {
                showView(
                    binding.includeError.root,
                    binding.includeError.tvMessage,
                    it.msg ?: getString(R.string.unknown_exception)
                )
            } else {
                hideView(binding.includeError.root)
            }
        })

        viewModel.repoList.observe(this, Observer {
            adapter.submitList(it)
        })

        viewModel.listEmpty.observe(this, Observer {
            if (it == true) {
                binding.emptyLayout.tvMessage.text = getString(R.string.no_team_msg)
            }
        })

        viewModel.close.observe(this, Observer {
            activity?.finish()
        })
    }

    private fun initViews() {
        val placeholder = PlaceHolderDrawable(binding.root.context)
        val errorDrawable = ContextCompat.getDrawable(
            binding.root.context,
            R.drawable.ic_soccer
        ).apply {
            DrawableCompat.setTint(
                this ?: return@apply,
                AttrUtils.convertAttrToColor(R.attr.colorError, binding.root.context)
            )
        }

        (activity as TeamDetailActivity?)?.setSupportActionBar(binding.toolbar)

        binding.toolbar.setNavigationIcon(R.drawable.ic_close)
        binding.toolbar.setNavigationOnClickListener {
            viewModel.handleNavigationIconClick()
        }

        val team = getTeam()
        binding.tvTitle.text = team?.name

        ImageLoader()
            .with(binding.imgTeamLogo)
            .placeholder(placeholder)
            .load(team?.crestUrl)
            .error(errorDrawable)
            .begin()

        binding.recyclerView.adapter = adapter
        binding.recyclerView.isNestedScrollingEnabled = true
        binding.recyclerView.setEmptyView(binding.emptyLayout.root)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.addItemDecoration(
            ItemDivider(context)
        )
        binding.swipeRefreshLayout.setOnRefreshListener {
            refresh()
        }
        binding.includeError.btnRetry.setOnClickListener {
            refresh()
        }
    }

    private fun refresh() {
        viewModel.refresh()
    }

    private fun loadData() {
        viewModel.loadData(getTeam())
    }

    private fun getTeam() = arguments?.getParcelable<Team>(Constants.EXTRAS)

    override fun getLayoutResourceId() = R.layout.fragment_team_detail

    override fun getBindingVariable() = BR._all

    override fun getViewModelClass() = TeamDetailViewModel::class.java

    companion object {
        fun newInstance(team: Team?): TeamDetailFragment {
            val fragment = TeamDetailFragment()
            val data = Bundle()
            data.putParcelable(Constants.EXTRAS, team)
            fragment.arguments = data
            return fragment
        }
    }


}