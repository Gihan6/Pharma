package com.qdreamcaller.creativemindstask.ui.base

import android.app.Application
import com.qdream.retrofit_kotlin_coroutines_example.di.appModules
import com.qdream.retrofit_kotlin_coroutines_example.di.viewModelModule

import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

import org.koin.core.context.startKoin

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