package com.kryptkode.footballfixtures.todaysfixtures

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.kryptkode.footballfixtures.BR
import com.kryptkode.footballfixtures.R
import com.kryptkode.footballfixtures.app.base.fragment.BaseFragment
import com.kryptkode.footballfixtures.app.data.models.todays.Match
import com.kryptkode.footballfixtures.app.utils.NetworkState
import com.kryptkode.footballfixtures.app.utils.Status
import com.kryptkode.footballfixtures.app.views.ItemDivider
import com.kryptkode.footballfixtures.databinding.FragmentTodaysBinding
import com.kryptkode.footballfixtures.todaysfixtures.adapter.TodaysFixturesAdapter
import com.kryptkode.footballfixtures.todaysfixtures.adapter.TodaysFixturesListener
import javax.inject.Inject

class TodaysFixturesFragment @Inject constructor() :
    BaseFragment<FragmentTodaysBinding, TodaysFixturesViewModel>() {

    private val fixturesListener = object : TodaysFixturesListener {
        override fun onItemClick(item: Match?) {

        }
    }

    private val adapter = TodaysFixturesAdapter(fixturesListener)

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
                getString(R.string.no_fixtures_msg)
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
                binding.emptyLayout.tvMessage.text = getString(R.string.no_fixtures_msg)
            }
        })
    }

    private fun initViews() {
        binding.recyclerView.adapter = adapter
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
        viewModel.loadData()
    }

    override fun getLayoutResourceId() = R.layout.fragment_todays

    override fun getBindingVariable() = BR._all

    override fun getViewModelClass() = TodaysFixturesViewModel::class.java
}