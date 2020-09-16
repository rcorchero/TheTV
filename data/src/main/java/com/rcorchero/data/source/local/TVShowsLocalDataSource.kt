package com.rcorchero.data.source.local

import com.rcorchero.data.entities.TVShowEntity
import com.rcorchero.domain.exception.CacheError
import com.rcorchero.domain.exception.DeleteSuccess
import com.rcorchero.domain.exception.SaveSuccess
import com.rcorchero.domain.functional.Either

interface TVShowsLocalDataSource {

    enum class TVShowsType {
        AIRING_TODAY, POPULAR, TOP_RATED
    }

    fun getTVShows(type: TVShowsType): Either<CacheError, List<TVShowEntity>>

    fun saveTVShows(
        type: TVShowsType,
        tvShowsList: List<TVShowEntity>
    ): Either<CacheError, SaveSuccess>

    fun deleteTVShows(type: TVShowsType): Either<CacheError, DeleteSuccess>
}