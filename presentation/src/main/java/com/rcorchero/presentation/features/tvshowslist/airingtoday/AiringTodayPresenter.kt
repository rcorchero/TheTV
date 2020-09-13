package com.rcorchero.presentation.features.tvshowslist.airingtoday

import com.rcorchero.domain.model.TVShow
import com.rcorchero.domain.usecase.GetAiringTodayTVShowsUseCase
import com.rcorchero.presentation.asynchrony.AsynchronyManager
import com.rcorchero.presentation.features.tvshowslist.TVShowsListView
import com.rcorchero.presentation.model.toViewModel

class AiringTodayPresenter(
    view: TVShowsListView,
    asynchronyManager: AsynchronyManager,
    private val getAiringTodayTVShowsUseCase: GetAiringTodayTVShowsUseCase
) :
    TVShowsListView by view,
    AsynchronyManager by asynchronyManager {

    private var currentShowsList = listOf<TVShow>()

    fun getAiringToday() {
        showLoading()

        launch(
            function = { getAiringTodayTVShowsUseCase() },
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