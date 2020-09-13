package com.rcorchero.data

import com.rcorchero.data.entities.TVShowEntity
import com.rcorchero.data.entities.toDomainModel
import com.rcorchero.data.source.local.TVShowsLocalDataSource
import com.rcorchero.data.source.local.TVShowsLocalDataSource.TVShowsType
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
            remoteDataSource.getAiringTodayTVShows().map {
                saveData(TVShowsType.AIRING_TODAY, it)
                it.toDomainModel()
            }
        } else {
            Either.Right(
                localDataSource.getTVShows(TVShowsType.AIRING_TODAY).map {
                    it.toDomainModel()
                }
            )
        }

    override suspend fun getPopular(): Either<Failure, List<TVShow>> =
        if (isOnline) {
            remoteDataSource.getPopularTVShows().map {
                saveData(TVShowsType.POPULAR, it)
                it.toDomainModel()
            }
        } else {
            Either.Right(
                localDataSource.getTVShows(TVShowsType.POPULAR).map {
                    it.toDomainModel()
                }
            )
        }

    override suspend fun getTopRated(): Either<Failure, List<TVShow>> =
        if (isOnline) {
            remoteDataSource.getTopRatedTVShows().map {
                saveData(TVShowsType.TOP_RATED, it)
                it.toDomainModel()
            }
        } else {
            Either.Right(
                localDataSource.getTVShows(TVShowsType.TOP_RATED).map {
                    it.toDomainModel()
                }
            )
        }

    private fun saveData(type: TVShowsType, movieEntityList: List<TVShowEntity>) {
        localDataSource.deleteTVShows(type)
        localDataSource.saveTVShows(type, movieEntityList)
    }
}