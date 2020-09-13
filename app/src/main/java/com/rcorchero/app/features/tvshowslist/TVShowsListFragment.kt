package com.rcorchero.app.features.tvshowslist

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.rcorchero.app.R
import com.rcorchero.app.core.extensions.gone
import com.rcorchero.app.core.extensions.visible
import com.rcorchero.presentation.features.tvshowslist.TVShowsListView
import com.rcorchero.presentation.model.TVShowView
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_tv_shows_list.*
import javax.inject.Inject

abstract class TVShowsListFragment : DaggerFragment(R.layout.fragment_tv_shows_list),
    TVShowsListView {

    @Inject
    lateinit var tvShowsListAdapter: TVShowsListAdapter

    abstract fun onViewRefreshed()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupList()
        setupSwipeToRefresh()
    }

    private fun setupList() = tvShowsList.apply {
        layoutManager = GridLayoutManager(context, 3)
        adapter = tvShowsListAdapter
        layoutAnimation =
            AnimationUtils.loadLayoutAnimation(context, R.anim.grid_layout_animation_from_bottom)
    }

    private fun setupSwipeToRefresh() = srlShowsList.apply {
        setColorSchemeResources(R.color.green)
        setProgressBackgroundColorSchemeResource(R.color.black)
        setOnRefreshListener {
            onViewRefreshed()
        }
    }

    override fun showLoading() {
        viewEmpty.gone()
        tvShowsList.gone()

        if (!srlShowsList.isRefreshing) {
            viewLoader.visible()
        }
    }

    override fun hideLoading() =
        if (srlShowsList.isRefreshing) {
            srlShowsList.isRefreshing = false
        } else {
            viewLoader.gone()
        }

    override fun renderTVShows(tvShows: List<TVShowView>) {
        tvShowsList.visible()

        tvShowsListAdapter.collection = tvShows
        tvShowsList.scheduleLayoutAnimation()
    }

    override fun showEmptyView() =
        viewEmpty.visible()

    override fun showError() =
        Snackbar.make(clRoot, getString(R.string.error_network), Snackbar.LENGTH_LONG).show()
}