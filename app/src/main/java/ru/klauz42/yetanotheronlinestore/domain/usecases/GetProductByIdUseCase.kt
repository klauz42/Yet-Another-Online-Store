package ru.klauz42.yetanotheronlinestore.domain.usecases

import kotlinx.coroutines.flow.Flow
import ru.klauz42.yetanotheronlinestore.domain.models.ProductsRepository
import ru.klauz42.yetanotheronlinestore.domain.models.entities.Product
import javax.inject.Inject

class GetProductByIdUseCase @Inject constructor(
    private val repository: ProductsRepository
) {
    operator fun invoke(id: String): Flow<Product?> {
        return repository.getProductById(id)
    }
}