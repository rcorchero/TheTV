package com.rcorchero.data.source.remote

import com.rcorchero.data.entities.TVShowListEntity
import com.rcorchero.data.entities.TVShowEntity
import com.rcorchero.domain.exception.Failure
import com.rcorchero.domain.functional.Either

class TVShowsRemoteDataSourceImpl(
    private val service: TVShowsService
) : TVShowsRemoteDataSource, RemoteDataSourceUtils {

    override fun getAiringTodayTVShows(): Either<Failure, List<TVShowEntity>> =
        request(
            call = service.airingToday(),
            transform = { it.results },
            default = TVShowListEntity()
        )

    override fun getPopularTVShows(): Either<Failure, List<TVShowEntity>> =
        request(
            call = service.popular(),
            transform = { it.results },
            default = TVShowListEntity()
        )

    override fun getTopRatedTVShows(): Either<Failure, List<TVShowEntity>> =
        request(
            call = service.topRated(),
            transform = { it.results },
            default = TVShowListEntity()
        )
}