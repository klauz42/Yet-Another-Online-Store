package ru.klauz42.yetanotheronlinestore.presentation

import ru.klauz42.yetanotheronlinestore.R
import ru.klauz42.yetanotheronlinestore.domain.models.entities.Product

data class ProductWithImages(
    val product: Product,
    val imagesResId: List<Int>,
) {
    companion object {
        fun from(product: Product): ProductWithImages {
            return when (product.id) {
                "cbf0c984-7c6c-4ada-82da-e29dc698bb50" -> ProductWithImages(
                    product,
                    listOf(R.drawable.item_6, R.drawable.item_5)
                )

                "54a876a5-2205-48ba-9498-cfecff4baa6e" -> ProductWithImages(
                    product,
                    listOf(R.drawable.item_1, R.drawable.item_2)
                )

                "75c84407-52e1-4cce-a73a-ff2d3ac031b3" -> ProductWithImages(
                    product,
                    listOf(R.drawable.item_5, R.drawable.item_6)
                )

                "16f88865-ae74-4b7c-9d85-b68334bb97db" -> ProductWithImages(
                    product,
                    listOf(R.drawable.item_3, R.drawable.item_4)
                )

                "26f88856-ae74-4b7c-9d85-b68334bb97db" -> ProductWithImages(
                    product,
                    listOf(R.drawable.item_2, R.drawable.item_3)
                )

                "15f88865-ae74-4b7c-9d81-b78334bb97db" -> ProductWithImages(
                    product,
                    listOf(R.drawable.item_6, R.drawable.item_1)
                )

                "88f88865-ae74-4b7c-9d81-b78334bb97db" -> ProductWithImages(
                    product,
                    listOf(R.drawable.item_4, R.drawable.item_3)
                )

                "55f58865-ae74-4b7c-9d81-b78334bb97db" -> ProductWithImages(
                    product,
                    listOf(R.drawable.item_1, R.drawable.item_2)
                )

                else -> ProductWithImages(product, listOf())
            }
        }
    }
}