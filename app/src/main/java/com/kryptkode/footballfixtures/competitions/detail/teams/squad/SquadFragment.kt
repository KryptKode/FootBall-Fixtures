package com.kryptkode.footballfixtures.competitions.detail.teams.squad

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
import com.kryptkode.footballfixtures.competitions.detail.teams.squad.adapter.SquadAdapter
import com.kryptkode.footballfixtures.databinding.FragmentTeamDetailBinding
import com.kryptkode.imageloader.ImageLoader
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class SquadFragment @Inject constructor() :
    BaseFragment<FragmentTeamDetailBinding, SquadViewModel>(), HasAndroidInjector {

    private val adapter = SquadAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        idlingResource?.setIdleState(false)
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
        binding.toolbar.setNavigationIcon(R.drawable.ic_close)
        binding.toolbar.setNavigationOnClickListener {
            viewModel.handleNavigationIconClick()
        }

        val team = getTeam()
        binding.tvTitle.text = team?.name ?: getString(R.string.app_name)

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
        viewModel.loadData(getTeam()?.id)
    }

    private fun getTeam() = arguments?.getParcelable<Team>(Constants.EXTRAS)

    override fun getLayoutResourceId() = R.layout.fragment_team_detail

    override fun getBindingVariable() = BR._all

    override fun getViewModelClass() = SquadViewModel::class.java

    companion object {
        fun newInstance(team: Team?): SquadFragment {
            val fragment = SquadFragment()
            val data = Bundle()
            data.putParcelable(Constants.EXTRAS, team)
            fragment.arguments = data
            return fragment
        }
    }


}