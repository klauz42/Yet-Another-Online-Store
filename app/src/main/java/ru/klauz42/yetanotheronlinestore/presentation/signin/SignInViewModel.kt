package ru.klauz42.yetanotheronlinestore.presentation.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import kotlinx.coroutines.runBlocking
import ru.klauz42.yetanotheronlinestore.domain.models.entities.UserData
import ru.klauz42.yetanotheronlinestore.domain.usecases.SetUserDataUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignInViewModel @Inject constructor(
    private val setUserDataUseCase: SetUserDataUseCase
) : ViewModel() {

    private val _firstName = MutableLiveData("")
    private val _secondName = MutableLiveData("")
    private val _phoneNumber = MutableLiveData("")

    val hasFirstNameError = _firstName.map {
        !isNameCorrect(it) && it.isNotEmpty()
    }
    val hasSecondNameError = _secondName.map {
        !isNameCorrect(it) && it.isNotEmpty()
    }
    val hasPhoneNumberError = _phoneNumber.map {
        !isPhoneNumberCorrect(it) && it.isNotEmpty()
    }

    private fun getCorrectnessStatus(): Boolean {
        return isNameCorrect(_firstName.value ?: "")
                && isNameCorrect(_secondName.value ?: "")
                && isPhoneNumberCorrect(_phoneNumber.value ?: "")
    }

    private val _canSignIn = MediatorLiveData<Boolean>().apply {
        addSource(_firstName) {
            value = getCorrectnessStatus()
        }
        addSource(_secondName) {
            value = getCorrectnessStatus()
        }
        addSource(_phoneNumber) {
            value = getCorrectnessStatus()
        }
    }
    val canSignIn: LiveData<Boolean> = _canSignIn


    fun updateFirstName(name: String) {
        _firstName.value = name
    }

    fun updateSecondName(name: String) {
        _secondName.value = name
    }

    fun updatePhoneNumber(number: String) {
        _phoneNumber.value = number
    }

    fun saveUserData() {
        val userData = UserData(_firstName.value!!, _secondName.value!!, _phoneNumber.value!!)
        runBlocking {
            setUserDataUseCase(userData)
        }

    }

    private fun isNameCorrect(name: String): Boolean {
        val pattern = Regex("^[а-яА-ЯёЁ]+$")
        return pattern.matches(name)
    }

    private fun isPhoneNumberCorrect(name: String): Boolean {
        val pattern = Regex("^\\+7\\d{10}\$")
        return pattern.matches(name)
    }
}