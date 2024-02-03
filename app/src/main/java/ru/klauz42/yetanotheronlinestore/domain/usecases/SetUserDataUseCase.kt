package ru.klauz42.yetanotheronlinestore.domain.usecases

import ru.klauz42.yetanotheronlinestore.domain.models.UserPreferencesRepository
import ru.klauz42.yetanotheronlinestore.domain.models.entities.UserData
import javax.inject.Inject

class SetUserDataUseCase @Inject constructor(
    private val repository: UserPreferencesRepository
) {
    suspend operator fun invoke(userData: UserData) {
        return repository.setUserData(userData)
    }
}