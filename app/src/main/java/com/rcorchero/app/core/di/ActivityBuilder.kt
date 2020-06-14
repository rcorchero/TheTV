package com.rcorchero.app.core.di

import com.rcorchero.app.features.TvShowsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector
    @PerActivity
    internal abstract fun bindTvShowsActivity(): TvShowsActivity
}