package com.rcorchero.presentation.features.airingtoday

import com.rcorchero.presentation.features.LoadingView
import com.rcorchero.presentation.model.TVShowView

interface AiringTodayView : LoadingView {
    fun renderTvShows(tvShows: List<TVShowView>)
    fun showEmptyView()
    fun showError()
}