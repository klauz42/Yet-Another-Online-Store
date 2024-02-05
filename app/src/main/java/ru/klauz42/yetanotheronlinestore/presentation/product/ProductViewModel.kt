package ru.klauz42.yetanotheronlinestore.presentation.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.klauz42.yetanotheronlinestore.domain.usecases.GetItemLikeStatusByIdUseCase
import ru.klauz42.yetanotheronlinestore.domain.usecases.GetProductByIdUseCase
import ru.klauz42.yetanotheronlinestore.domain.usecases.SetLikedProductStatusUseCase
import ru.klauz42.yetanotheronlinestore.presentation.ProductWithImages
import javax.inject.Inject

class ProductViewModel @Inject constructor(
    private val getProductByIdUseCase: GetProductByIdUseCase,
    getItemLikeStatusByIdUseCase: GetItemLikeStatusByIdUseCase,
    private val setLikedProductStatusUseCase: SetLikedProductStatusUseCase,
) : ViewModel() {
    private val productIdLiveData = MutableLiveData<String>()

    fun loadProductItem(id: String) {
        productIdLiveData.value = id
    }

    val productWithImages: LiveData<ProductWithImages?> = productIdLiveData.switchMap { id ->
        getProductByIdUseCase(id).asLiveData(viewModelScope.coroutineContext).map {
            it?.let {
                ProductWithImages.from(it)
            }
        }
    }

    val isFavoriteLiveData = productIdLiveData.switchMap { id ->
        liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
            val isFavorite = getItemLikeStatusByIdUseCase(id)
            emit(isFavorite)
        }
    }

    fun updateLikedStatus(isLiked: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            productIdLiveData.value?.let {
                setLikedProductStatusUseCase(it, isLiked)
            }
        }
    }
}
