package com.rcorchero.presentation.features.airingtoday

import com.rcorchero.domain.model.TVShow
import com.rcorchero.domain.usecase.GetAiringTodayTVShowsUseCase
import com.rcorchero.presentation.asynchrony.AsynchronyManager
import com.rcorchero.presentation.model.toViewModel

class AiringTodayPresenter(
    airingTodayView: AiringTodayView,
    asynchronyManager: AsynchronyManager,
    private val getAiringTodayTVShowsUseCase: GetAiringTodayTVShowsUseCase
) :
    AiringTodayView by airingTodayView,
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
                    renderTvShows(showsList.map { it.toViewModel() })
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