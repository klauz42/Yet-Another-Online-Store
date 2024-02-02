package ru.klauz42.yetanotheronlinestore

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}