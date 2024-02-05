package ru.klauz42.yetanotheronlinestore.domain.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.klauz42.yetanotheronlinestore.domain.models.FavoritesRepository
import javax.inject.Inject

class GetFavoriteIdsCountUseCase @Inject constructor(
    private val repository: FavoritesRepository
) {
    operator fun invoke(): Flow<Int> {
        return repository.getFavoritesId().map {
            it.size
        }
    }
}