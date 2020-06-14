package com.rcorchero.data.source.remote

import com.rcorchero.data.entities.TVShowEntity
import com.rcorchero.domain.exception.Failure
import com.rcorchero.domain.functional.Either

interface TVShowsRemoteDataSource {

    fun getAiringTodayTvShows(): Either<Failure, List<TVShowEntity>>
}