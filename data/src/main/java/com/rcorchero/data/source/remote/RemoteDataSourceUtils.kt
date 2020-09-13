package com.rcorchero.data.source.remote

import com.rcorchero.domain.exception.Failure
import com.rcorchero.domain.functional.Either
import retrofit2.Call

interface RemoteDataSourceUtils {

    fun <T, R> request(call: Call<T>, transform: (T) -> R, default: T): Either<Failure, R> {
        return try {
            val response = call.execute()
            when (response.isSuccessful) {
                true -> Either.Right(transform((response.body() ?: default)))
                false -> Either.Left(Failure.ServerError)
            }
        } catch (exception: Throwable) {
            Either.Left(Failure.ServerError)
        }
    }
}