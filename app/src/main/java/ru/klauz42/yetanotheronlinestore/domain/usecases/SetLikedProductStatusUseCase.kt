package ru.klauz42.yetanotheronlinestore.domain.usecases

import ru.klauz42.yetanotheronlinestore.domain.models.FavoritesRepository
import javax.inject.Inject

class SetLikedProductStatusUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) {
    suspend operator fun invoke(id: String, isLiked: Boolean) {
        if (isLiked)
            favoritesRepository.addProductIdToFavorites(id)
        else
            favoritesRepository.removeProductIdToFavorites(id)
    }
}