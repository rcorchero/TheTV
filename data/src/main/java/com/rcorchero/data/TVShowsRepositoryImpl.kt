package com.rcorchero.data

import com.rcorchero.data.entities.TVShowEntity
import com.rcorchero.data.entities.toDomainModel
import com.rcorchero.data.source.local.TVShowsLocalDataSource
import com.rcorchero.data.source.remote.TVShowsRemoteDataSource
import com.rcorchero.domain.exception.Failure
import com.rcorchero.domain.functional.Either
import com.rcorchero.domain.functional.map
import com.rcorchero.domain.model.TVShow
import com.rcorchero.domain.repository.TVShowsRepository

class TVShowsRepositoryImpl(
    private val isOnline: Boolean,
    private val localDataSource: TVShowsLocalDataSource,
    private val remoteDataSource: TVShowsRemoteDataSource
) : TVShowsRepository {

    override suspend fun getAiringToday(): Either<Failure, List<TVShow>> =
        if (isOnline) {
            remoteDataSource.getAiringTodayTvShows().map {
                saveData(it)
                it.toDomainModel()
            }
        } else {
            Either.Right(
                localDataSource.getAiringTodayTvShows().map {
                    it.toDomainModel()
                }
            )
        }

    private fun saveData(movieEntityList: List<TVShowEntity>) {
        localDataSource.deleteAiringTodayTvShows()
        localDataSource.saveAiringTodayTvShows(movieEntityList)
    }
}