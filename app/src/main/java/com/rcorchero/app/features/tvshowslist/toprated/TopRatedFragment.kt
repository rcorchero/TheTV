package com.rcorchero.app.features.tvshowslist.toprated

import android.os.Bundle
import android.view.View
import com.rcorchero.app.features.tvshowslist.TVShowsListFragment
import com.rcorchero.presentation.features.tvshowslist.toprated.TopRatedPresenter
import javax.inject.Inject

class TopRatedFragment : TVShowsListFragment() {

    @Inject
    lateinit var presenter: TopRatedPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getTopRated()
    }

    override fun onViewRefreshed() = getTopRated()

    private fun getTopRated() = presenter.getTopRated()

    override fun onDestroy() {
        super.onDestroy()
        presenter.cancel()
    }
}