package ru.klauz42.yetanotheronlinestore.di.modules

import dagger.Binds
import dagger.Module
import ru.klauz42.yetanotheronlinestore.data.MockedProductsRepository
import ru.klauz42.yetanotheronlinestore.domain.models.ProductsRepository


@Module
interface ProductsRepositoryModule {
    @Suppress("FunctionName")
    @Binds
    fun bindsUserPreferencesRepository_to_LocalUserPreferencesRepository(
        repository: MockedProductsRepository
    ): ProductsRepository
}