package com.rcorchero.domain.repository

import com.rcorchero.domain.exception.Failure
import com.rcorchero.domain.functional.Either
import com.rcorchero.domain.model.TVShow

interface TVShowsRepository {

    suspend fun getAiringToday(): Either<Failure, List<TVShow>>
}