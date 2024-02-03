package ru.klauz42.yetanotheronlinestore.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.klauz42.yetanotheronlinestore.domain.models.UserPreferencesRepository
import ru.klauz42.yetanotheronlinestore.domain.models.entities.UserData
import javax.inject.Inject


class LocalUserPreferencesRepository @Inject constructor(private val dataStore: DataStore<Preferences>) :
    UserPreferencesRepository {

    companion object {
        val FIRST_NAME_KEY = stringPreferencesKey("first_name")
        val SECOND_NAME_KEY = stringPreferencesKey("second_name")
        val PHONE_NUMBER_KEY = stringPreferencesKey("phone_number")
    }

    override fun getUserData(): Flow<UserData> = dataStore.data.map { preferences ->
        UserData(
            firstName = preferences[FIRST_NAME_KEY] ?: "",
            secondName = preferences[SECOND_NAME_KEY] ?: "",
            phoneNumber = preferences[PHONE_NUMBER_KEY] ?: ""
        )
    }

    override suspend fun setUserData(userData: UserData) {
        dataStore.edit { preferences ->
            preferences[FIRST_NAME_KEY] = userData.firstName
            preferences[SECOND_NAME_KEY] = userData.secondName
            preferences[PHONE_NUMBER_KEY] = userData.phoneNumber
        }
    }
}
