package ru.klauz42.yetanotheronlinestore.di.modules

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.klauz42.yetanotheronlinestore.data.FavoriteItemsDao
import ru.klauz42.yetanotheronlinestore.data.FavoritesDatabase
import javax.inject.Singleton


const val DATABASE_NAME = "favorites-database"

@Module
object DatabaseModule {
    @Provides
    @Singleton
    fun provideTodoItemDatabase(context: Context): FavoritesDatabase {
        return Room.databaseBuilder(
            context,
            FavoritesDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideTodoItemDao(database: FavoritesDatabase): FavoriteItemsDao {
        return database.favoriteItemsDao()
    }
}