package ru.klauz42.yetanotheronlinestore.domain.models

import kotlinx.coroutines.flow.Flow
import ru.klauz42.yetanotheronlinestore.domain.models.entities.Product

interface FavoritesRepository {
    fun getFavorites(): Flow<List<Product>>
    fun getFavoritesId(): Flow<List<String>>
    suspend fun isProductFavorite(id: String): Boolean
    suspend fun addProductIdToFavorites(id: String)
    suspend fun removeProductIdToFavorites(id: String)
    suspend fun clearFavorites()
}