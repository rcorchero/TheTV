package com.rcorchero.app.core.di.features.tvshowslist.airingtoday

import com.rcorchero.domain.usecase.GetAiringTodayTVShowsUseCase
import com.rcorchero.presentation.asynchrony.AsynchronyManager
import com.rcorchero.presentation.features.tvshowslist.airingtoday.AiringTodayPresenter
import com.rcorchero.app.core.di.asynchrony.AsynchronyModule
import com.rcorchero.app.features.tvshowslist.airingtoday.AiringTodayFragment
import dagger.Module
import dagger.Provides

@Module(includes = [AsynchronyModule::class])
class AiringTodayModule {

    @Provides
    internal fun providePresenter(
        airingTodayFragment: AiringTodayFragment,
        asynchronyManager: AsynchronyManager,
        getAiringTodayTVShowsUseCase: GetAiringTodayTVShowsUseCase
    ): AiringTodayPresenter =
        AiringTodayPresenter(
            view = airingTodayFragment,
            asynchronyManager = asynchronyManager,
            getAiringTodayTVShowsUseCase = getAiringTodayTVShowsUseCase
        )
}