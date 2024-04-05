package com.sookmyung.umbrellafriend

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import timber.log.Timber
import com.sookmyung.umbrellafriend.BuildConfig

class UmbrellaFriendApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}