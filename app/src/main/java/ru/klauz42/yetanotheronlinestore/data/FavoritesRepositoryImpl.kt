package ru.klauz42.yetanotheronlinestore.data

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import ru.klauz42.yetanotheronlinestore.domain.models.FavoritesRepository
import ru.klauz42.yetanotheronlinestore.domain.models.ProductsRepository
import ru.klauz42.yetanotheronlinestore.domain.models.entities.FavoriteItemEntity
import ru.klauz42.yetanotheronlinestore.domain.models.entities.Product
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoritesRepositoryImpl @Inject constructor(
    private val productsRepository: ProductsRepository,
    private val favoriteItemsDao: FavoriteItemsDao
) : FavoritesRepository {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getFavorites(): Flow<List<Product>> = favoriteItemsDao.getAllIds()
        .flatMapLatest { favoriteItemEntities ->
            val ids = favoriteItemEntities.map { it.id }
            productsRepository.getProductsById(ids)
        }

    override fun getFavoritesId(): Flow<List<String>> {
        return getFavorites().map { products ->
            products.map { it.id }
        }
    }

    override suspend fun isProductFavorite(id: String): Boolean {
        return getFavoritesId().first().contains(id)
    }

    override suspend fun addProductIdToFavorites(id: String) {
        favoriteItemsDao.insertId(FavoriteItemEntity(id))
    }

    override suspend fun removeProductIdToFavorites(id: String) {
        favoriteItemsDao.deleteId(FavoriteItemEntity(id))
    }
}