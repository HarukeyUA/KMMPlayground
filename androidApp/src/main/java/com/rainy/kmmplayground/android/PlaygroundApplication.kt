package com.rainy.kmmplayground.android

import android.app.Application
import com.rainy.kmmplayground.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

class PlaygroundApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@PlaygroundApplication)
            androidLogger(Level.WARNING)
        }
    }
}