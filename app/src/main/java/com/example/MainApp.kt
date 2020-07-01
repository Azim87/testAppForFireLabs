package com.example

import android.app.Application
import com.example.testapp.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApp : Application() {
    private lateinit var instance: MainApp


    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApp)
            modules(appModules)
        }
        instance = MainApp()
    }

    fun getInstance(): MainApp {
        return instance
    }
}