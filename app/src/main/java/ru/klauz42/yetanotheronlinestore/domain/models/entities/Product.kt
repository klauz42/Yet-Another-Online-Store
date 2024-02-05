package ru.klauz42.yetanotheronlinestore.domain.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@Entity(tableName = "products")
@JsonClass(generateAdapter = true)
data class Product(
    val available: Int = 0,
    val description: String = "",
    val feedback: Feedback = Feedback(),
    @PrimaryKey val id: String = "",
    val info: List<Info> = listOf(),
    val ingredients: String = "",
    val price: Price = Price(),
    val subtitle: String = "",
    val tags: List<String> = listOf(),
    val title: String = ""
)