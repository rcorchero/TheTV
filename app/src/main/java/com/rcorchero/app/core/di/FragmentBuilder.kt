package com.rcorchero.app.core.di

import com.rcorchero.app.core.di.features.airingtoday.AiringTodayModule
import com.rcorchero.app.features.airingtoday.AiringTodayFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilder {

    @ContributesAndroidInjector(modules = [(AiringTodayModule::class)])
    @PerFragment
    internal abstract fun bindAiringTodayFragment(): AiringTodayFragment
}