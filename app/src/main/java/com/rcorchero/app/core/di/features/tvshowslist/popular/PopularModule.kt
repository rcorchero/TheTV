package com.rcorchero.app.core.di.features.tvshowslist.popular

import com.rcorchero.app.core.di.asynchrony.AsynchronyModule
import com.rcorchero.app.features.tvshowslist.popular.PopularFragment
import com.rcorchero.domain.usecase.GetPopularTVShowsUseCase
import com.rcorchero.presentation.asynchrony.AsynchronyManager
import com.rcorchero.presentation.features.tvshowslist.popular.PopularPresenter
import dagger.Module
import dagger.Provides

@Module(includes = [AsynchronyModule::class])
class PopularModule {

    @Provides
    internal fun providePresenter(
        popularFragment: PopularFragment,
        asynchronyManager: AsynchronyManager,
        getPopularTVShowsUseCase: GetPopularTVShowsUseCase
    ): PopularPresenter =
        PopularPresenter(
            view = popularFragment,
            asynchronyManager = asynchronyManager,
            getPopularTVShowsUseCase = getPopularTVShowsUseCase
        )
}