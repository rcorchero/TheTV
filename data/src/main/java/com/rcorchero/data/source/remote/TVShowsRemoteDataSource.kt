package com.rcorchero.data.source.remote

import com.rcorchero.data.entities.TVShowEntity
import com.rcorchero.domain.exception.ServerError
import com.rcorchero.domain.functional.Either

interface TVShowsRemoteDataSource {

    fun getAiringTodayTVShows(): Either<ServerError, List<TVShowEntity>>

    fun getPopularTVShows(): Either<ServerError, List<TVShowEntity>>

    fun getTopRatedTVShows(): Either<ServerError, List<TVShowEntity>>
}