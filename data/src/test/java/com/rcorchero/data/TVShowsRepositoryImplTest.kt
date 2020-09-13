package com.rcorchero.data

import com.rcorchero.data.source.local.TVShowsLocalDataSource
import com.rcorchero.data.source.local.TVShowsLocalDataSource.TVShowsType
import com.rcorchero.data.source.remote.TVShowsRemoteDataSource
import com.rcorchero.domain.functional.Either
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class TVShowsRepositoryImplTest {

    private lateinit var sut: TVShowsRepositoryImpl

    @MockK
    private lateinit var localDataSource: TVShowsLocalDataSource

    @MockK
    private lateinit var remoteDataSource: TVShowsRemoteDataSource


    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)

        sut = TVShowsRepositoryImpl(true, localDataSource, remoteDataSource)
    }

    @Test
    fun `when calling airing today, should call remote data source if online`() {
        coEvery { remoteDataSource.getAiringTodayTVShows() } returns Either.Right(emptyList())

        runBlocking {
            sut.getAiringToday()

            verify { remoteDataSource.getAiringTodayTVShows() }
            verify(exactly = 0) { localDataSource.getTVShows(TVShowsType.AIRING_TODAY) }
        }
    }

    @Test
    fun `when calling popular, should call remote data source if online`() {
        coEvery { remoteDataSource.getPopularTVShows() } returns Either.Right(emptyList())

        runBlocking {
            sut.getPopular()

            verify { remoteDataSource.getPopularTVShows() }
            verify(exactly = 0) { localDataSource.getTVShows(TVShowsType.POPULAR) }
        }
    }

    @Test
    fun `when calling top rated, should call remote data source if online`() {
        coEvery { remoteDataSource.getTopRatedTVShows() } returns Either.Right(emptyList())

        runBlocking {
            sut.getTopRated()

            verify { remoteDataSource.getTopRatedTVShows() }
            verify(exactly = 0) { localDataSource.getTVShows(TVShowsType.TOP_RATED) }
        }
    }

    @Test
    fun `when calling airing today, should save tv shows in local data source if remote call is successful`() {
        coEvery { remoteDataSource.getAiringTodayTVShows() } returns Either.Right(emptyList())

        runBlocking {
            val response = sut.getAiringToday()

            verify {
                assertTrue(response.isRight)
                localDataSource.deleteTVShows(TVShowsType.AIRING_TODAY)
                localDataSource.saveTVShows(TVShowsType.AIRING_TODAY, any())
            }
        }
    }

    @Test
    fun `when calling popular, should save tv shows in local data source if remote call is successful`() {
        coEvery { remoteDataSource.getPopularTVShows() } returns Either.Right(emptyList())

        runBlocking {
            val response = sut.getPopular()

            verify {
                assertTrue(response.isRight)
                localDataSource.deleteTVShows(TVShowsType.POPULAR)
                localDataSource.saveTVShows(TVShowsType.POPULAR, any())
            }
        }
    }

    @Test
    fun `when calling top rated, should save tv shows in local data source if remote call is successful`() {
        coEvery { remoteDataSource.getTopRatedTVShows() } returns Either.Right(emptyList())

        runBlocking {
            val response = sut.getTopRated()

            verify {
                assertTrue(response.isRight)
                localDataSource.deleteTVShows(TVShowsType.TOP_RATED)
                localDataSource.saveTVShows(TVShowsType.TOP_RATED, any())
            }
        }
    }

    @Test
    fun `when calling airing today, should call local data source if offline`() {
        sut = TVShowsRepositoryImpl(false, localDataSource, remoteDataSource)

        coEvery { localDataSource.getTVShows(TVShowsType.AIRING_TODAY) } returns emptyList()

        runBlocking {
            sut.getAiringToday()

            verify { localDataSource.getTVShows(TVShowsType.AIRING_TODAY) }
            verify(exactly = 0) { remoteDataSource.getAiringTodayTVShows() }
        }
    }

    @Test
    fun `when calling popular, should call local data source if offline`() {
        sut = TVShowsRepositoryImpl(false, localDataSource, remoteDataSource)

        coEvery { localDataSource.getTVShows(TVShowsType.POPULAR) } returns emptyList()

        runBlocking {
            sut.getPopular()

            verify { localDataSource.getTVShows(TVShowsType.POPULAR) }
            verify(exactly = 0) { remoteDataSource.getPopularTVShows() }
        }
    }

    @Test
    fun `when calling top rated, should call local data source if offline`() {
        sut = TVShowsRepositoryImpl(false, localDataSource, remoteDataSource)

        coEvery { localDataSource.getTVShows(TVShowsType.TOP_RATED) } returns emptyList()

        runBlocking {
            sut.getTopRated()

            verify { localDataSource.getTVShows(TVShowsType.TOP_RATED) }
            verify(exactly = 0) { remoteDataSource.getTopRatedTVShows() }
        }
    }
}