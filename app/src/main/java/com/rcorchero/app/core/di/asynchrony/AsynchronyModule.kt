package com.rcorchero.app.core.di.asynchrony

import com.rcorchero.presentation.asynchrony.AsynchronyManager
import com.rcorchero.presentation.asynchrony.AsynchronyManagerImpl
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

@Module
class AsynchronyModule(private val job: Job = Job()) {

    @Provides
    fun provideAsynchronyManager(): AsynchronyManager =
        AsynchronyManagerImpl(
            coroutineContext = Dispatchers.Main + job,
            io = Dispatchers.IO
        )
}