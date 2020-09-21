package com.qdreamcaller.creativemindstask.ui.base

import android.app.Application
import appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import viewModelModule

  class App : Application() {

    override fun onCreate() {
        super.onCreate()

        // Unique initialization of Dependency Injection library to allow the use of application context
        startKoin {
            androidContext(this@App)
            androidLogger()
            modules(listOf(appModules, viewModelModule))
        }


    }

}