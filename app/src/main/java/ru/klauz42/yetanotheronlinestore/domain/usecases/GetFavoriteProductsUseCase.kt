package ru.klauz42.yetanotheronlinestore.domain.usecases

import kotlinx.coroutines.flow.Flow
import ru.klauz42.yetanotheronlinestore.domain.models.FavoritesRepository
import ru.klauz42.yetanotheronlinestore.domain.models.entities.Product
import javax.inject.Inject

class GetFavoriteProductsUseCase @Inject constructor(
    private val repository: FavoritesRepository
) {
    operator fun invoke(): Flow<List<Product>> {
        return repository.getFavorites()
    }
}