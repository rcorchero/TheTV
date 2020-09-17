package com.rcorchero.presentation.features.tvshowslist.popular

import com.rcorchero.domain.exception.ServerError
import com.rcorchero.domain.functional.Either
import com.rcorchero.domain.model.TVShow
import com.rcorchero.domain.usecase.GetPopularTVShowsUseCase
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

class PopularPresenterTest {

    private lateinit var sut: PopularPresenter

    @MockK(relaxUnitFun = true)
    private lateinit var view: TVShowsListView

    @MockK
    private lateinit var getPopularTVShowsUseCase: GetPopularTVShowsUseCase

    private lateinit var asynchronyManager: AsynchronyManager

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        asynchronyManager =
            AsynchronyManagerImpl(TestCoroutineDispatcher(), TestCoroutineDispatcher())
        sut = PopularPresenter(view, asynchronyManager, getPopularTVShowsUseCase)
    }

    @Test
    fun `show loading when get popular is called`() {
        coEvery { getPopularTVShowsUseCase.invoke() }.returns(Either.Right(emptyList()))

        sut.getPopular()

        verify { view.showLoading() }
    }

    @Test
    fun `call use case only once when get popular is called`() {
        sut.getPopular()

        coVerify(exactly = 1) { getPopularTVShowsUseCase.invoke() }
    }

    @Test
    fun `hide loading when popular call is success`() {
        coEvery { getPopularTVShowsUseCase.invoke() }.returns(Either.Right(emptyList()))

        sut.getPopular()

        verify { view.hideLoading() }
    }

    @Test
    fun `show empty view when tv shows list is empty`() {
        coEvery { getPopularTVShowsUseCase.invoke() }.returns(Either.Right(emptyList()))

        sut.getPopular()

        verify { view.showEmptyView() }
    }

    @Test
    fun `render tv shows list when list is not empty`() {
        coEvery { getPopularTVShowsUseCase.invoke() }.returns(
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

        sut.getPopular()

        verify { view.renderTVShows(any()) }
    }

    @Test
    fun `hide loading when popular call has failed`() {
        coEvery { getPopularTVShowsUseCase.invoke() }.returns(Either.Left(ServerError))

        sut.getPopular()

        verify { view.hideLoading() }
    }

    @Test
    fun `show error when popular call has failed`() {
        coEvery { getPopularTVShowsUseCase.invoke() }.returns(Either.Left(ServerError))

        sut.getPopular()

        verify { view.showError() }
    }

    @Test
    fun `show empty view when popular call has failed and tv shows list is empty`() {
        coEvery { getPopularTVShowsUseCase.invoke() }.returns(Either.Left(ServerError))

        sut.getPopular()

        verify { view.showEmptyView() }
    }
}