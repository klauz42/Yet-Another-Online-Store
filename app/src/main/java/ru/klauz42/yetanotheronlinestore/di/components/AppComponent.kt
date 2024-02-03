package ru.klauz42.yetanotheronlinestore.di.components

import androidx.lifecycle.ViewModelProvider
import dagger.Component
import ru.klauz42.yetanotheronlinestore.MainApp
import ru.klauz42.yetanotheronlinestore.di.modules.AppModule
import ru.klauz42.yetanotheronlinestore.di.modules.DataStoreModule
import ru.klauz42.yetanotheronlinestore.di.modules.UserDataRepositoryModule
import ru.klauz42.yetanotheronlinestore.di.modules.ViewModelModule
import ru.klauz42.yetanotheronlinestore.domain.models.UserPreferencesRepository
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AppModule::class,
        DataStoreModule::class,
        UserDataRepositoryModule::class,
        ViewModelModule::class,
    ]
)
interface AppComponent {
    fun inject(application: MainApp)

    @Component.Factory
    interface Factory {
        fun create(appModule: AppModule): AppComponent
    }

    fun getUserDataRepository(): UserPreferencesRepository
    fun getViewModelFactory(): ViewModelProvider.Factory
}