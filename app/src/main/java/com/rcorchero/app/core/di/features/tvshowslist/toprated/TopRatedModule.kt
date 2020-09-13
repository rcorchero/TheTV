package com.rcorchero.app.core.di.features.tvshowslist.toprated

import com.rcorchero.app.core.di.asynchrony.AsynchronyModule
import com.rcorchero.app.features.tvshowslist.toprated.TopRatedFragment
import com.rcorchero.domain.usecase.GetTopRatedTVShowsUseCase
import com.rcorchero.presentation.asynchrony.AsynchronyManager
import com.rcorchero.presentation.features.tvshowslist.toprated.TopRatedPresenter
import dagger.Module
import dagger.Provides

@Module(includes = [AsynchronyModule::class])
class TopRatedModule {

    @Provides
    internal fun providePresenter(
        topRatedFragment: TopRatedFragment,
        asynchronyManager: AsynchronyManager,
        getTopRatedTVShowsUseCase: GetTopRatedTVShowsUseCase
    ): TopRatedPresenter =
        TopRatedPresenter(
            view = topRatedFragment,
            asynchronyManager = asynchronyManager,
            getTopRatedTVShowsUseCase = getTopRatedTVShowsUseCase
        )
}