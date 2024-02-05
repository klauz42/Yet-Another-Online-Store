package ru.klauz42.yetanotheronlinestore.domain.usecases

import ru.klauz42.yetanotheronlinestore.domain.models.FavoritesRepository
import javax.inject.Inject

class GetItemLikeStatusByIdUseCase @Inject constructor(
    private val repository: FavoritesRepository
) {
    suspend operator fun invoke(id: String): Boolean {
        return repository.isProductFavorite(id)
    }
}