package com.example.wwmweaponscompose

import android.app.Application
import timber.log.Timber

class WWMApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}