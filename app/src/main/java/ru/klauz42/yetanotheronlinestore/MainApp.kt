package ru.klauz42.yetanotheronlinestore

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import ru.klauz42.yetanotheronlinestore.di.components.AppComponent
import ru.klauz42.yetanotheronlinestore.di.components.DaggerAppComponent
import ru.klauz42.yetanotheronlinestore.di.modules.AppModule


val Context.appComponent: AppComponent
    get() = when (this) {
        is MainApp -> appComponent
        else -> this.applicationContext.appComponent
    }

class MainApp : Application() {
    lateinit var appComponent: AppComponent
        protected set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(AppModule(this))

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}