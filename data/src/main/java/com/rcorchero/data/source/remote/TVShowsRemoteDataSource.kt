package com.rcorchero.data.source.remote

import com.rcorchero.data.entities.TVShowEntity
import com.rcorchero.domain.exception.Failure
import com.rcorchero.domain.functional.Either

interface TVShowsRemoteDataSource {

    fun getAiringTodayTVShows(): Either<Failure, List<TVShowEntity>>

    fun getPopularTVShows(): Either<Failure, List<TVShowEntity>>

    fun getTopRatedTVShows(): Either<Failure, List<TVShowEntity>>
}