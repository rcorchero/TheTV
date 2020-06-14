package com.rcorchero.presentation.features.airingtoday

import com.rcorchero.domain.exception.Failure
import com.rcorchero.domain.functional.Either
import com.rcorchero.domain.model.TVShow
import com.rcorchero.domain.usecase.GetAiringTodayTVShowsUseCase
import com.rcorchero.presentation.asynchrony.AsynchronyManager
import com.rcorchero.presentation.asynchrony.AsynchronyManagerImpl
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Before
import org.junit.Test

class AiringTodayPresenterTest {

    private lateinit var sut: AiringTodayPresenter

    @MockK(relaxUnitFun = true)
    private lateinit var view: AiringTodayView

    @MockK
    private lateinit var getAiringTodayTVShowsUseCase: GetAiringTodayTVShowsUseCase

    private lateinit var asynchronyManager: AsynchronyManager

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        asynchronyManager =
            AsynchronyManagerImpl(TestCoroutineDispatcher(), TestCoroutineDispatcher())
        sut = AiringTodayPresenter(view, asynchronyManager, getAiringTodayTVShowsUseCase)
    }

    @Test
    fun `show loading when get airing today is called`() {
        coEvery { getAiringTodayTVShowsUseCase.invoke() }.returns(Either.Right(emptyList()))

        sut.getAiringToday()

        verify { view.showLoading() }
    }

    @Test
    fun `call use case only once when get airing today is called`() {
        sut.getAiringToday()

        coVerify(exactly = 1) { getAiringTodayTVShowsUseCase.invoke() }
    }

    @Test
    fun `hide loading when airing today call is success`() {
        coEvery { getAiringTodayTVShowsUseCase.invoke() }.returns(Either.Right(emptyList()))

        sut.getAiringToday()

        verify { view.hideLoading() }
    }

    @Test
    fun `show empty view when tv shows list is empty`() {
        coEvery { getAiringTodayTVShowsUseCase.invoke() }.returns(Either.Right(emptyList()))

        sut.getAiringToday()

        verify { view.showEmptyView() }
    }

    @Test
    fun `render tv shows list when list is not empty`() {
        coEvery { getAiringTodayTVShowsUseCase.invoke() }.returns(
            Either.Right(
                listOf(
                    TVShow(
                        id = 1,
                        name = "Test TV Show",
                        voteAverage = 10.0,
                        posterUrl = ""
                    )
                )
            )
        )

        sut.getAiringToday()

        verify { view.renderTvShows(any()) }
    }

    @Test
    fun `hide loading when airing today call has failed`() {
        coEvery { getAiringTodayTVShowsUseCase.invoke() }.returns(Either.Left(Failure.DomainError))

        sut.getAiringToday()

        verify { view.hideLoading() }
    }

    @Test
    fun `show error when airing today call has failed`() {
        coEvery { getAiringTodayTVShowsUseCase.invoke() }.returns(Either.Left(Failure.DomainError))

        sut.getAiringToday()

        verify { view.showError() }
    }

    @Test
    fun `show empty view when airing today call has failed and tv shows list is empty`() {
        coEvery { getAiringTodayTVShowsUseCase.invoke() }.returns(Either.Left(Failure.DomainError))

        sut.getAiringToday()

        verify { view.showEmptyView() }
    }
}