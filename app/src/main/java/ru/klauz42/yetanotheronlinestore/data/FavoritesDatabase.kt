package ru.klauz42.yetanotheronlinestore.data

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.klauz42.yetanotheronlinestore.domain.models.entities.FavoriteItemEntity


@Database(entities = [FavoriteItemEntity::class], version = 1)
abstract class FavoritesDatabase : RoomDatabase() {
    abstract fun favoriteItemsDao(): FavoriteItemsDao
}