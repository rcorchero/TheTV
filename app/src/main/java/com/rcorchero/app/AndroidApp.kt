package com.rcorchero.app

import com.rcorchero.app.core.di.app.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class AndroidApp : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<AndroidApp> {
        val component = DaggerAppComponent.builder().application(this).build()
        component.inject(this)
        return component
    }
}