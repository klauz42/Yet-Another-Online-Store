package ru.klauz42.yetanotheronlinestore.domain.usecases

import ru.klauz42.yetanotheronlinestore.domain.models.ProductsRepository
import ru.klauz42.yetanotheronlinestore.domain.models.entities.FilterTag
import ru.klauz42.yetanotheronlinestore.domain.models.entities.Product
import javax.inject.Inject

class GetFilteredByTagProductsUseCase @Inject constructor(
    private val repository: ProductsRepository
) {
    //todo: remove hardcode
    operator fun invoke(tag: FilterTag): List<Product> {
        return if (tag == FilterTag.ALL) repository.getProducts() else repository.getFilteredByTagProducts(
            tag
        )
    }
}