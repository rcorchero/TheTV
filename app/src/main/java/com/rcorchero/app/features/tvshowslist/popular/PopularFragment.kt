package com.rcorchero.app.features.tvshowslist.popular

import android.os.Bundle
import android.view.View
import com.rcorchero.app.features.tvshowslist.TVShowsListFragment
import com.rcorchero.presentation.features.tvshowslist.popular.PopularPresenter
import javax.inject.Inject

class PopularFragment : TVShowsListFragment() {

    @Inject
    lateinit var presenter: PopularPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getPopular()
    }

    override fun onViewRefreshed() = getPopular()

    private fun getPopular() = presenter.getPopular()

    override fun onDestroy() {
        super.onDestroy()
        presenter.cancel()
    }
}