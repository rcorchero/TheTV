package com.rcorchero.domain.usecase

import com.rcorchero.domain.functional.Either
import com.rcorchero.domain.repository.TVShowsRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetTopRatedTVShowsUseCaseTest {

    private lateinit var sut: GetTopRatedTVShowsUseCase

    @MockK
    private lateinit var repository: TVShowsRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        sut = GetTopRatedTVShowsUseCase(repository)

        coEvery { repository.getTopRated() }.returns(Either.Right(emptyList()))
    }

    @Test
    fun `should get data from repository`() {
        runBlocking { sut.invoke() }

        coVerify(exactly = 1) { repository.getTopRated() }
    }
}