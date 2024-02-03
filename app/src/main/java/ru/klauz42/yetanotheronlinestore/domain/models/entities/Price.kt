package ru.klauz42.yetanotheronlinestore.domain.models.entities

data class Price(
    val discount: Int = 0,
    val price: String = "",
    val priceWithDiscount: String = "",
    val unit: String = ""
)