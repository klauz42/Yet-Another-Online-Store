package ru.klauz42.yetanotheronlinestore.domain.usecases

import kotlinx.coroutines.flow.Flow
import ru.klauz42.yetanotheronlinestore.domain.models.UserPreferencesRepository
import ru.klauz42.yetanotheronlinestore.domain.models.entities.UserData
import javax.inject.Inject

class GetUserDataUseCase @Inject constructor(
    private val repository: UserPreferencesRepository
) {
    operator fun invoke(): Flow<UserData> {
        return repository.getUserData()
    }
}