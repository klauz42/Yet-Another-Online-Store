package ru.klauz42.yetanotheronlinestore.domain.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites_table")
data class FavoriteItemEntity(
    @PrimaryKey val id: String
)