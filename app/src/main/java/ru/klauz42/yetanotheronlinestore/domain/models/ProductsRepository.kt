package ru.klauz42.yetanotheronlinestore.domain.models

import kotlinx.coroutines.flow.Flow
import ru.klauz42.yetanotheronlinestore.domain.models.entities.FilterTag
import ru.klauz42.yetanotheronlinestore.domain.models.entities.Product

interface ProductsRepository {
    fun getProductsFlow(): Flow<List<Product>>
    fun getProducts(): List<Product>
    fun getFilteredByTagProducts(tag: FilterTag): List<Product>
    fun getProductById(id: String): Flow<Product?>
    fun getProductsById(idList: List<String>): Flow<List<Product>>
}