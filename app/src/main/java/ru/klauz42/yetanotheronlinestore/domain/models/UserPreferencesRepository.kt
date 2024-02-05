package ru.klauz42.yetanotheronlinestore.domain.models

import kotlinx.coroutines.flow.Flow
import ru.klauz42.yetanotheronlinestore.domain.models.entities.UserData


interface UserPreferencesRepository {
    fun getUserData(): Flow<UserData>
    suspend fun setUserData(userData: UserData)
}
