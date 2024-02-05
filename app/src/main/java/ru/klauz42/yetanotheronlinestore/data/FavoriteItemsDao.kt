package ru.klauz42.yetanotheronlinestore.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.klauz42.yetanotheronlinestore.domain.models.entities.FavoriteItemEntity

@Dao
interface FavoriteItemsDao {
    @Query("SELECT * FROM favorites_table")
    fun getAllIds(): Flow<List<FavoriteItemEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertId(idEntity: FavoriteItemEntity)

    @Delete
    suspend fun deleteId(idEntity: FavoriteItemEntity)

    @Query("DELETE FROM favorites_table")
    suspend fun clearTable()
}