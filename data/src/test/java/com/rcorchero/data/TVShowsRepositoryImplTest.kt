package com.rcorchero.data

import com.rcorchero.data.source.local.TVShowsLocalDataSource
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
    fun `should call remote data source if online`() {
        coEvery { remoteDataSource.getAiringTodayTvShows() } returns Either.Right(emptyList())

        runBlocking {
            sut.getAiringToday()

            verify { remoteDataSource.getAiringTodayTvShows() }
            verify(exactly = 0) { localDataSource.getAiringTodayTvShows() }
        }
    }

    @Test
    fun `should save tv shows in local data source if remote call is successful`() {
        coEvery { remoteDataSource.getAiringTodayTvShows() } returns Either.Right(emptyList())

        runBlocking {
            val response = sut.getAiringToday()

            verify {
                assertTrue(response.isRight)
                localDataSource.deleteAiringTodayTvShows()
                localDataSource.saveAiringTodayTvShows(any())
            }
        }
    }

    @Test
    fun `should call local data source if offline`() {
        sut = TVShowsRepositoryImpl(false, localDataSource, remoteDataSource)

        coEvery { localDataSource.getAiringTodayTvShows() } returns emptyList()

        runBlocking {
            sut.getAiringToday()

            verify { localDataSource.getAiringTodayTvShows() }
            verify(exactly = 0) { remoteDataSource.getAiringTodayTvShows() }
        }
    }
}