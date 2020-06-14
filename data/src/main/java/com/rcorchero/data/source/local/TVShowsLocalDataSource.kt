package com.rcorchero.data.source.local

import com.rcorchero.data.entities.TVShowEntity

interface TVShowsLocalDataSource {

    fun getAiringTodayTvShows(): List<TVShowEntity>
    fun saveAiringTodayTvShows(tvShowsList: List<TVShowEntity>)
    fun deleteAiringTodayTvShows()
}