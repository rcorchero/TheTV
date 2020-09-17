package com.rcorchero.presentation.features.tvshowslist.toprated

import com.rcorchero.domain.exception.ServerError
import com.rcorchero.domain.functional.Either
import com.rcorchero.domain.model.TVShow
import com.rcorchero.domain.usecase.GetTopRatedTVShowsUseCase
import com.rcorchero.presentation.asynchrony.AsynchronyManager
import com.rcorchero.presentation.asynchrony.AsynchronyManagerImpl
import com.rcorchero.presentation.features.tvshowslist.TVShowsListView
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Before
import org.junit.Test

class TopRatedPresenterTest {

    private lateinit var sut: TopRatedPresenter

    @MockK(relaxUnitFun = true)
    private lateinit var view: TVShowsListView

    @MockK
    private lateinit var getTopRatedTVShowsUseCase: GetTopRatedTVShowsUseCase

    private lateinit var asynchronyManager: AsynchronyManager

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        asynchronyManager =
            AsynchronyManagerImpl(TestCoroutineDispatcher(), TestCoroutineDispatcher())
        sut = TopRatedPresenter(view, asynchronyManager, getTopRatedTVShowsUseCase)
    }

    @Test
    fun `show loading when get top rated is called`() {
        coEvery { getTopRatedTVShowsUseCase.invoke() }.returns(Either.Right(emptyList()))

        sut.getTopRated()

        verify { view.showLoading() }
    }

    @Test
    fun `call use case only once when get top rated is called`() {
        sut.getTopRated()

        coVerify(exactly = 1) { getTopRatedTVShowsUseCase.invoke() }
    }

    @Test
    fun `hide loading when top rated call is success`() {
        coEvery { getTopRatedTVShowsUseCase.invoke() }.returns(Either.Right(emptyList()))

        sut.getTopRated()

        verify { view.hideLoading() }
    }

    @Test
    fun `show empty view when tv shows list is empty`() {
        coEvery { getTopRatedTVShowsUseCase.invoke() }.returns(Either.Right(emptyList()))

        sut.getTopRated()

        verify { view.showEmptyView() }
    }

    @Test
    fun `render tv shows list when list is not empty`() {
        coEvery { getTopRatedTVShowsUseCase.invoke() }.returns(
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

        sut.getTopRated()

        verify { view.renderTVShows(any()) }
    }

    @Test
    fun `hide loading when top rated call has failed`() {
        coEvery { getTopRatedTVShowsUseCase.invoke() }.returns(Either.Left(ServerError))

        sut.getTopRated()

        verify { view.hideLoading() }
    }

    @Test
    fun `show error when top rated call has failed`() {
        coEvery { getTopRatedTVShowsUseCase.invoke() }.returns(Either.Left(ServerError))

        sut.getTopRated()

        verify { view.showError() }
    }

    @Test
    fun `show empty view when top rated call has failed and tv shows list is empty`() {
        coEvery { getTopRatedTVShowsUseCase.invoke() }.returns(Either.Left(ServerError))

        sut.getTopRated()

        verify { view.showEmptyView() }
    }
}