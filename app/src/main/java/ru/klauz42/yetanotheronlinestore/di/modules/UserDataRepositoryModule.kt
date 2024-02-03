package ru.klauz42.yetanotheronlinestore.di.modules

import dagger.Binds
import dagger.Module
import ru.klauz42.yetanotheronlinestore.data.LocalUserPreferencesRepository
import ru.klauz42.yetanotheronlinestore.domain.models.UserPreferencesRepository


@Module
interface UserDataRepositoryModule {
    @Suppress("FunctionName")
    @Binds
    fun bindsUserPreferencesRepository_to_LocalUserPreferencesRepository(
        repository: LocalUserPreferencesRepository
    ): UserPreferencesRepository
}