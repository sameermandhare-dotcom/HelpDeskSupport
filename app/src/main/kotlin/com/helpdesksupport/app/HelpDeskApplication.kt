package com.helpdesksupport.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Application entry point with Hilt dependency injection setup
 */
@HiltAndroidApp
class HelpDeskApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
