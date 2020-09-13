package com.rcorchero.app.features.tvshowslist.airingtoday

import android.os.Bundle
import android.view.View
import com.rcorchero.app.features.tvshowslist.TVShowsListFragment
import com.rcorchero.presentation.features.tvshowslist.airingtoday.AiringTodayPresenter
import javax.inject.Inject

class AiringTodayFragment : TVShowsListFragment() {

    @Inject
    lateinit var presenter: AiringTodayPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getAiringToday()
    }

    override fun onViewRefreshed() = getAiringToday()

    private fun getAiringToday() = presenter.getAiringToday()

    override fun onDestroy() {
        super.onDestroy()
        presenter.cancel()
    }
}