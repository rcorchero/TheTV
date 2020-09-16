package com.rcorchero.data.source.remote

import com.rcorchero.data.entities.TVShowEntity
import com.rcorchero.data.entities.TVShowListEntity
import com.rcorchero.domain.exception.ServerError
import com.rcorchero.domain.functional.Either

class TVShowsRemoteDataSourceImpl(
    private val service: TVShowsService
) : TVShowsRemoteDataSource, RemoteDataSourceUtils {

    override fun getAiringTodayTVShows(): Either<ServerError, List<TVShowEntity>> =
        request(
            call = service.airingToday(),
            transform = { it.results },
            default = TVShowListEntity()
        )

    override fun getPopularTVShows(): Either<ServerError, List<TVShowEntity>> =
        request(
            call = service.popular(),
            transform = { it.results },
            default = TVShowListEntity()
        )

    override fun getTopRatedTVShows(): Either<ServerError, List<TVShowEntity>> =
        request(
            call = service.topRated(),
            transform = { it.results },
            default = TVShowListEntity()
        )
}