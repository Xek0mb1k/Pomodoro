package com.xekombik.pomodoro.app

import android.app.Application
import com.xekombik.pomodoro.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        // start Koin
        startKoin{
            androidContext(this@App)
            modules(appModule)
        }
    }

}