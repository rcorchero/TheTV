package com.rcorchero.presentation.features.tvshowslist.toprated

import com.rcorchero.domain.model.TVShow
import com.rcorchero.domain.usecase.GetTopRatedTVShowsUseCase
import com.rcorchero.presentation.asynchrony.AsynchronyManager
import com.rcorchero.presentation.features.tvshowslist.TVShowsListView
import com.rcorchero.presentation.model.toViewModel

class TopRatedPresenter(
    view: TVShowsListView,
    asynchronyManager: AsynchronyManager,
    private val getTopRatedTVShowsUseCase: GetTopRatedTVShowsUseCase
) :
    TVShowsListView by view,
    AsynchronyManager by asynchronyManager {

    private var currentShowsList = listOf<TVShow>()

    fun getTopRated() {
        showLoading()

        launch(
            function = { getTopRatedTVShowsUseCase() },
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