package com.rcorchero.data.source.remote

import com.rcorchero.data.entities.AiringTodayEntity
import com.rcorchero.data.entities.TVShowEntity
import com.rcorchero.domain.exception.Failure
import com.rcorchero.domain.exception.Failure.ServerError
import com.rcorchero.domain.functional.Either
import com.rcorchero.domain.functional.Either.Left
import com.rcorchero.domain.functional.Either.Right
import retrofit2.Call

class TVShowsRemoteDataSourceImpl(
    private val service: TVShowsService
) : TVShowsRemoteDataSource {

    override fun getAiringTodayTvShows(): Either<Failure, List<TVShowEntity>> =
        request(
            call = service.airingToday(),
            transform = { it.results },
            default = AiringTodayEntity()
        )

    private fun <T, R> request(call: Call<T>, transform: (T) -> R, default: T): Either<Failure, R> {
        return try {
            val response = call.execute()
            when (response.isSuccessful) {
                true -> Right(transform((response.body() ?: default)))
                false -> Left(ServerError)
            }
        } catch (exception: Throwable) {
            Left(ServerError)
        }
    }
}