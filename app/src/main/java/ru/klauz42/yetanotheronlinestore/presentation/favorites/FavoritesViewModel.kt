package ru.klauz42.yetanotheronlinestore.presentation.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.klauz42.yetanotheronlinestore.domain.models.entities.Product
import ru.klauz42.yetanotheronlinestore.domain.usecases.GetFavoriteProductsUseCase
import ru.klauz42.yetanotheronlinestore.domain.usecases.GetFavoritesIdUseCase
import ru.klauz42.yetanotheronlinestore.domain.usecases.SetLikedProductStatusUseCase
import javax.inject.Inject

class FavoritesViewModel @Inject constructor(
    getFavoritesUseCase: GetFavoriteProductsUseCase,
    private val setLikedProductStatusUseCase: SetLikedProductStatusUseCase,
    getFavoriteIds: GetFavoritesIdUseCase,
) : ViewModel() {

    fun updateLikedStatus(id: String, isLiked: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            setLikedProductStatusUseCase(id, isLiked)
        }
    }

    private val _productsLiveData =
        getFavoritesUseCase().asLiveData(viewModelScope.coroutineContext)
    val productsLiveData: LiveData<List<Product>> = _productsLiveData

    val favoriteIds = getFavoriteIds().asLiveData(viewModelScope.coroutineContext)
}
