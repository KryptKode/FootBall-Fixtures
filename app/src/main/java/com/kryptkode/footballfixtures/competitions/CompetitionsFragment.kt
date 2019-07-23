package com.kryptkode.footballfixtures.competitions

import android.os.Bundle
import android.view.View
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kryptkode.footballfixtures.BR
import com.kryptkode.footballfixtures.R
import com.kryptkode.footballfixtures.app.base.fragment.BaseFragment
import com.kryptkode.footballfixtures.app.data.models.competition.Competition
import com.kryptkode.footballfixtures.app.utils.NetworkState
import com.kryptkode.footballfixtures.app.utils.Status
import com.kryptkode.footballfixtures.app.views.ItemDivider
import com.kryptkode.footballfixtures.competitions.adapter.CompetitionsAdapter
import com.kryptkode.footballfixtures.competitions.adapter.CompetitionsListener
import com.kryptkode.footballfixtures.competitions.detail.CompetitionsDetailActivity
import com.kryptkode.footballfixtures.databinding.FragmentCompetitionBinding
import javax.inject.Inject

class CompetitionsFragment @Inject constructor() :
    BaseFragment<FragmentCompetitionBinding, CompetitionViewModel>() {

    @VisibleForTesting
    var clickItem: Competition? = null

    private val competitionsListener = object : CompetitionsListener {
        override fun onItemClick(competition: Competition?) {
            clickItem = competition
            viewModel.handleItemClick(competition)
        }
    }

    private val adapter = CompetitionsAdapter(competitionsListener)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        idlingResource?.setIdleState(false)
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
                getString(R.string.no_competitions_msg)
            }


            if (it.status == Status.FAILED) {
                showView(
                    binding.includeError.root,
                    binding.includeError.tvMessage,
                    getString(it.msg ?: R.string.unknown_exception)
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
                binding.emptyLayout.tvMessage.text = getString(R.string.no_competitions_msg)
            }
        })

        viewModel.openDetail.observe(this, Observer {
            val data = CompetitionsDetailActivity.getBundleExtras(it)
            startNewActivity(CompetitionsDetailActivity::class.java, data = data)
        })
    }

    private fun initViews() {
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setEmptyView(binding.emptyLayout.root)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.addItemDecoration(
            ItemDivider(context)
        )
        binding.recyclerView.adapter?.registerAdapterDataObserver(
            object : RecyclerView.AdapterDataObserver() {
                override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                    idlingResource?.setIdleState(true)
                }
            }
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
        viewModel.loadData()
    }


    override fun getLayoutResourceId() = R.layout.fragment_competition

    override fun getBindingVariable() = BR._all

    override fun getViewModelClass() = CompetitionViewModel::class.java
}