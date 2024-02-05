package ru.klauz42.yetanotheronlinestore.domain.usecases

import kotlinx.coroutines.flow.Flow
import ru.klauz42.yetanotheronlinestore.domain.models.FavoritesRepository
import javax.inject.Inject

class GetFavoritesIdUseCase @Inject constructor(
    private val repository: FavoritesRepository
) {
    operator fun invoke(): Flow<List<String>> {
        return repository.getFavoritesId()
    }
}