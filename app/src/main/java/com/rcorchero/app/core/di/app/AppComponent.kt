package com.rcorchero.app.core.di.app

import android.app.Application
import com.rcorchero.app.AndroidApp
import com.rcorchero.app.core.di.ActivityBuilder
import com.rcorchero.app.core.di.FragmentBuilder
import com.rcorchero.app.core.di.data.DataModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        DataModule::class,
        ActivityBuilder::class,
        FragmentBuilder::class
    ]
)
interface AppComponent : AndroidInjector<AndroidApp> {

    override fun inject(app: AndroidApp)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }
}