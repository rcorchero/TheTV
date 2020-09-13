package com.rcorchero.app.core.di

import com.rcorchero.app.core.di.features.tvshowslist.airingtoday.AiringTodayModule
import com.rcorchero.app.core.di.features.tvshowslist.popular.PopularModule
import com.rcorchero.app.core.di.features.tvshowslist.toprated.TopRatedModule
import com.rcorchero.app.features.tvshowslist.airingtoday.AiringTodayFragment
import com.rcorchero.app.features.tvshowslist.popular.PopularFragment
import com.rcorchero.app.features.tvshowslist.toprated.TopRatedFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilder {

    @ContributesAndroidInjector(modules = [(AiringTodayModule::class)])
    @PerFragment
    internal abstract fun bindAiringTodayFragment(): AiringTodayFragment

    @ContributesAndroidInjector(modules = [(PopularModule::class)])
    @PerFragment
    internal abstract fun bindPopularFragment(): PopularFragment

    @ContributesAndroidInjector(modules = [(TopRatedModule::class)])
    @PerFragment
    internal abstract fun bindTopRatedFragment(): TopRatedFragment
}