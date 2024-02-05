package ru.klauz42.yetanotheronlinestore.presentation.catalog

import ru.klauz42.yetanotheronlinestore.domain.models.entities.Product

data class ProductWithImages(
    val product: Product,
    val imagesResId: List<Int>,
    val isLiked: Boolean = false,
)