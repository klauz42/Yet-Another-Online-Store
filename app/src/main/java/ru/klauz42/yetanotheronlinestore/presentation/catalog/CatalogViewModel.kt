package ru.klauz42.yetanotheronlinestore.presentation.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.klauz42.yetanotheronlinestore.domain.models.entities.FilterTag
import ru.klauz42.yetanotheronlinestore.domain.models.entities.Product
import ru.klauz42.yetanotheronlinestore.domain.models.entities.SortType
import ru.klauz42.yetanotheronlinestore.domain.models.entities.TagCheckBoxItem
import ru.klauz42.yetanotheronlinestore.domain.usecases.GetFavoritesIdUseCase
import ru.klauz42.yetanotheronlinestore.domain.usecases.GetFilteredByTagProductsUseCase
import ru.klauz42.yetanotheronlinestore.domain.usecases.SetLikedProductStatusUseCase
import javax.inject.Inject

class CatalogViewModel @Inject constructor(
    private val getFilteredByTagProductsUseCase: GetFilteredByTagProductsUseCase,
    getFavoritesIdUseCase: GetFavoritesIdUseCase,
    private val setLikedProductStatusUseCase: SetLikedProductStatusUseCase,
) : ViewModel() {
    private val _sortType: MutableLiveData<SortType> = MutableLiveData(SortType.BY_RATING)
    val sortType: LiveData<SortType> = _sortType

    fun setSortType(type: SortType) {
        _sortType.postValue(type)
    }

    private val _tagLiveData: MutableLiveData<FilterTag?> = MutableLiveData(FilterTag.ALL)

    fun updateTag(filterTag: FilterTag, isChecked: Boolean) {
        _tagLiveData.postValue(if (isChecked) filterTag else null)
    }

    fun updateLikedStatus(id: String, isLiked: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            setLikedProductStatusUseCase(id, isLiked)
        }
    }

    private val defaultTagsList = listOf(
        TagCheckBoxItem(FilterTag.ALL, text = "Смотреть все"),
        TagCheckBoxItem(FilterTag.FACE, text = "Лицо"),
        TagCheckBoxItem(FilterTag.BODY, text = "Тело"),
        TagCheckBoxItem(FilterTag.SUNTAN, text = "Загар"),
        TagCheckBoxItem(FilterTag.MASK, text = "Маски"),
    )

    private fun getTagCheckboxItemList(tag: FilterTag?): List<TagCheckBoxItem> {
        return defaultTagsList.map {
            it.copy(isSelected = (it.id == tag))
        }
    }

    val tagListLiveData: LiveData<List<TagCheckBoxItem>> = _tagLiveData.map { tag ->
        getTagCheckboxItemList(tag)
    }

    private val _productsLiveData = MediatorLiveData<List<Product>>().apply {
        addSource(_tagLiveData) {
            postValue(getSortAndFilteredProductList())
        }
        addSource(_sortType) {
            postValue(getSortAndFilteredProductList())
        }
    }
    val productsLiveData: LiveData<List<Product>> = _productsLiveData

    private fun getSortAndFilteredProductList(): List<Product> {
        val filteredList = getFilteredByTagProductsUseCase(_tagLiveData.value ?: FilterTag.ALL)
        return when (_sortType.value ?: SortType.BY_RATING) {
            SortType.BY_RATING -> filteredList.sortedBy { -it.feedback.rating }
            SortType.PRICE_DESCENDING -> filteredList.sortedBy { -(it.price.priceWithDiscount).toLong() }
            SortType.PRICE_ASCENDING -> filteredList.sortedBy { (it.price.priceWithDiscount).toLong() }
        }
    }

    val favoriteIds = getFavoritesIdUseCase().asLiveData(viewModelScope.coroutineContext)
}
