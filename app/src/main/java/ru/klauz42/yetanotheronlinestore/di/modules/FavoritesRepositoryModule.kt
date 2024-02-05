package ru.klauz42.yetanotheronlinestore.di.modules

import dagger.Binds
import dagger.Module
import ru.klauz42.yetanotheronlinestore.data.FavoritesRepositoryImpl
import ru.klauz42.yetanotheronlinestore.domain.models.FavoritesRepository


@Module
interface FavoritesRepositoryModule {
    @Suppress("FunctionName")
    @Binds
    fun bindsFavoritesRepository_to_FavoritesRepositoryImpl(
        repository: FavoritesRepositoryImpl
    ): FavoritesRepository
}