package com.rcorchero.presentation.features.tvshowslist

import com.rcorchero.presentation.features.LoadingView
import com.rcorchero.presentation.model.TVShowView

interface TVShowsListView : LoadingView {
    fun renderTVShows(tvShows: List<TVShowView>)
    fun showEmptyView()
    fun showError()
}