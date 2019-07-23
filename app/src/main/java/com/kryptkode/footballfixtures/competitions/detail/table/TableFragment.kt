package com.kryptkode.footballfixtures.competitions.detail.table

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kryptkode.footballfixtures.BR
import com.kryptkode.footballfixtures.R
import com.kryptkode.footballfixtures.app.base.fragment.BaseFragment
import com.kryptkode.footballfixtures.app.data.models.competition.Competition
import com.kryptkode.footballfixtures.app.utils.Constants
import com.kryptkode.footballfixtures.app.utils.NetworkState
import com.kryptkode.footballfixtures.app.utils.Status
import com.kryptkode.footballfixtures.app.views.ItemDivider
import com.kryptkode.footballfixtures.competitions.detail.table.adapter.TableAdapter
import com.kryptkode.footballfixtures.databinding.FragmentTableBinding
import javax.inject.Inject

class TableFragment @Inject constructor() : BaseFragment<FragmentTableBinding, TableViewModel>() {

    private val adapter = TableAdapter()

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
                getString(R.string.no_table_msg)
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
                binding.emptyLayout.tvMessage.text = getString(R.string.no_table_msg)
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
        val competition = arguments?.getParcelable<Competition>(Constants.EXTRAS)
        viewModel.loadData(competition?.id)
    }


    override fun getLayoutResourceId() = R.layout.fragment_table

    override fun getBindingVariable() = BR._all

    override fun getViewModelClass() = TableViewModel::class.java
}
