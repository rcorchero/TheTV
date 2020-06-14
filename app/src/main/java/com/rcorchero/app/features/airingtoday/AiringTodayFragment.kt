package com.rcorchero.app.features.airingtoday

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.rcorchero.app.R
import com.rcorchero.app.core.extensions.gone
import com.rcorchero.app.core.extensions.visible
import com.rcorchero.presentation.features.airingtoday.AiringTodayPresenter
import com.rcorchero.presentation.features.airingtoday.AiringTodayView
import com.rcorchero.presentation.model.TVShowView
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_airing_today.*
import javax.inject.Inject

class AiringTodayFragment : DaggerFragment(R.layout.fragment_airing_today), AiringTodayView {

    @Inject
    lateinit var presenter: AiringTodayPresenter

    @Inject
    lateinit var airingTodayAdapter: AiringTodayAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupList()
        setupSwipeToRefresh()
        loadAiringTodayTvShows()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.cancel()
    }

    private fun setupList() = tvShowsList.apply {
        layoutManager = GridLayoutManager(context, 3)
        adapter = airingTodayAdapter
        layoutAnimation =
            AnimationUtils.loadLayoutAnimation(context, R.anim.grid_layout_animation_from_bottom)
    }

    private fun setupSwipeToRefresh() = srlShowsList.apply {
        setColorSchemeResources(R.color.green)
        setProgressBackgroundColorSchemeResource(R.color.black)
        setOnRefreshListener {
            loadAiringTodayTvShows()
        }
    }

    private fun loadAiringTodayTvShows() =
        presenter.getAiringToday()

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

    override fun renderTvShows(tvShows: List<TVShowView>) {
        tvShowsList.visible()

        airingTodayAdapter.collection = tvShows
        tvShowsList.scheduleLayoutAnimation()
    }

    override fun showEmptyView() =
        viewEmpty.visible()

    override fun showError() =
        Snackbar.make(clRoot, getString(R.string.error_network), Snackbar.LENGTH_LONG).show()
}