package com.kryptkode.footballfixtures.competitions.detail.teams

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.kryptkode.footballfixtures.BR
import com.kryptkode.footballfixtures.R
import com.kryptkode.footballfixtures.app.base.fragment.BaseFragment
import com.kryptkode.footballfixtures.app.data.models.competition.Competition
import com.kryptkode.footballfixtures.app.data.models.team.Team
import com.kryptkode.footballfixtures.app.utils.Constants
import com.kryptkode.footballfixtures.app.utils.NetworkState
import com.kryptkode.footballfixtures.app.utils.Status
import com.kryptkode.footballfixtures.app.views.ItemDivider
import com.kryptkode.footballfixtures.app.views.StaggeredGridSpacingItemDecoration
import com.kryptkode.footballfixtures.competitions.detail.fixtures.FixturesViewModel
import com.kryptkode.footballfixtures.competitions.detail.teams.adapter.TeamAdapter
import com.kryptkode.footballfixtures.competitions.detail.teams.adapter.TeamListener
import com.kryptkode.footballfixtures.competitions.detail.teams.detail.TeamDetailFragment
import com.kryptkode.footballfixtures.databinding.FragmentTeamsBinding
import javax.inject.Inject

class TeamsFragment @Inject constructor() : BaseFragment<FragmentTeamsBinding, TeamsViewModel>() {

    private val teamListener = object : TeamListener {
        override fun onItemClick(item: Team?) {
            val fragment = TeamDetailFragment.newInstance(item)
            fragment.show(fragmentManager ?: return, fragment.javaClass.name)
        }
    }

    private val adapter = TeamAdapter(teamListener)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
        loadData()
    }

    private fun initObservers() {
        viewModel.networkState.observe(this, Observer {
            binding.swipeRefreshLayout.isRefreshing = it == NetworkState.LOADING
            binding.emptyLayout.tvMessage.text = if (it == NetworkState.LOADING) {
                getString(R.string.loading)
            } else {
                getString(R.string.no_table_msg)
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
                binding.emptyLayout.tvMessage.text = getString(R.string.no_table_msg)
            }
        })
    }

    private fun initViews() {
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setEmptyView(binding.emptyLayout.root)
        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(3, RecyclerView.VERTICAL)
        binding.recyclerView.addItemDecoration(
            StaggeredGridSpacingItemDecoration(
                resources.getDimension(
                    R.dimen.teams_item_padding
                ).toInt(), true
            )
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
        val competition = arguments?.getParcelable<Competition>(Constants.EXTRAS)
        viewModel.loadData(competition?.id)
    }

    override fun getLayoutResourceId() = R.layout.fragment_teams

    override fun getBindingVariable() = BR._all

    override fun getViewModelClass() = TeamsViewModel::class.java
}