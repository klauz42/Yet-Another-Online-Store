package ru.klauz42.yetanotheronlinestore.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import ru.klauz42.yetanotheronlinestore.domain.usecases.GetFavoriteIdsCountUseCase
import ru.klauz42.yetanotheronlinestore.domain.usecases.GetUserDataUseCase
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    getUserDataUseCase: GetUserDataUseCase,
    getFavoriteIdsCountUseCase: GetFavoriteIdsCountUseCase,
) : ViewModel() {

    val userData = getUserDataUseCase().asLiveData(viewModelScope.coroutineContext)
    val favoritesCount = getFavoriteIdsCountUseCase().asLiveData(viewModelScope.coroutineContext)
}
