package com.rcorchero.data.source.local

import com.rcorchero.data.entities.TVShowEntity

interface TVShowsLocalDataSource {

    enum class TVShowsType {
        AIRING_TODAY, POPULAR, TOP_RATED
    }

    fun getTVShows(type: TVShowsType): List<TVShowEntity>
    fun saveTVShows(type: TVShowsType, tvShowsList: List<TVShowEntity>)
    fun deleteTVShows(type: TVShowsType)
}