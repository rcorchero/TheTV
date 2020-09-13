package com.rcorchero.presentation.features.tvshowslist.popular

import com.rcorchero.domain.model.TVShow
import com.rcorchero.domain.usecase.GetPopularTVShowsUseCase
import com.rcorchero.presentation.asynchrony.AsynchronyManager
import com.rcorchero.presentation.features.tvshowslist.TVShowsListView
import com.rcorchero.presentation.model.toViewModel

class PopularPresenter(
    view: TVShowsListView,
    asynchronyManager: AsynchronyManager,
    private val getPopularTVShowsUseCase: GetPopularTVShowsUseCase
) :
    TVShowsListView by view,
    AsynchronyManager by asynchronyManager {

    private var currentShowsList = listOf<TVShow>()

    fun getPopular() {
        showLoading()

        launch(
            function = { getPopularTVShowsUseCase() },
            success = { showsList ->
                currentShowsList = showsList

                hideLoading()

                if (showsList.isEmpty()) {
                    showEmptyView()
                } else {
                    renderTVShows(showsList.map { it.toViewModel() })
                }
            },
            error = {
                hideLoading()
                showError()

                if (currentShowsList.isEmpty()) {
                    showEmptyView()
                }
            }
        )
    }
}