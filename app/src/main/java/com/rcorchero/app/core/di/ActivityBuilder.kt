package com.rcorchero.app.core.di

import com.rcorchero.app.features.TVShowsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector
    @PerActivity
    internal abstract fun bindTVShowsActivity(): TVShowsActivity
}