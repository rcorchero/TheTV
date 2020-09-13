package com.rcorchero.domain.usecase

import com.rcorchero.domain.exception.Failure
import com.rcorchero.domain.functional.Either
import com.rcorchero.domain.model.TVShow
import com.rcorchero.domain.repository.TVShowsRepository
import javax.inject.Inject

class GetTopRatedTVShowsUseCase @Inject constructor(private val repository: TVShowsRepository) {

    suspend operator fun invoke(): Either<Failure, List<TVShow>> = repository.getTopRated()
}