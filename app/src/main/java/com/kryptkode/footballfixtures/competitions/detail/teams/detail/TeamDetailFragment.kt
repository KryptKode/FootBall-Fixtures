package com.kryptkode.footballfixtures.competitions.detail.teams.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kryptkode.footballfixtures.BR
import com.kryptkode.footballfixtures.R
import com.kryptkode.footballfixtures.app.base.fragment.BaseFragment
import com.kryptkode.footballfixtures.app.data.models.competition.Competition
import com.kryptkode.footballfixtures.app.data.models.team.Team
import com.kryptkode.footballfixtures.app.utils.AttrUtils
import com.kryptkode.footballfixtures.app.utils.Constants
import com.kryptkode.footballfixtures.app.utils.NetworkState
import com.kryptkode.footballfixtures.app.utils.Status
import com.kryptkode.footballfixtures.app.views.ItemDivider
import com.kryptkode.footballfixtures.app.views.PlaceHolderDrawable
import com.kryptkode.footballfixtures.app.views.StaggeredGridSpacingItemDecoration
import com.kryptkode.footballfixtures.competitions.detail.teams.TeamsFragment
import com.kryptkode.footballfixtures.competitions.detail.teams.detail.adapter.TeamDetailAdapter
import com.kryptkode.footballfixtures.databinding.FragmentTeamDetailBinding
import com.kryptkode.imageloader.ImageLoader
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.HasFragmentInjector
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class TeamDetailFragment @Inject constructor() : BottomSheetDialogFragment(), HasAndroidInjector {

    protected lateinit var binding: FragmentTeamDetailBinding
    protected lateinit var viewModel: TeamDetailViewModel


    @Inject
    protected lateinit var viewModeFactory: ViewModelProvider.Factory


    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    private val adapter = TeamDetailAdapter()

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModeFactory)
            .get(TeamDetailViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return initBinding(inflater, container)
    }

    private fun initBinding(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_team_detail, container, false)
        binding.setVariable(BR._all, viewModel)
        binding.executePendingBindings()
        return binding.root
    }

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

        viewModel.close.observe(this, Observer {
            dismiss()
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

    override fun onStart() {
        super.onStart()
        val dialog = dialog as BottomSheetDialog?
        val bottomSheet =
            dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout?

        val behavior = BottomSheetBehavior.from(bottomSheet)
        behavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {

            }
        })

        behavior.state = BottomSheetBehavior.STATE_EXPANDED

    }

    override fun androidInjector(): AndroidInjector<Any>? {
        return androidInjector
    }

    private fun showView(view: View, textView: TextView, message: String) =
        (parentFragment as TeamsFragment?)?.showView(view, textView, message)

    private fun hideView(view: View) =
        (parentFragment as TeamsFragment?)?.hideView(view)

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