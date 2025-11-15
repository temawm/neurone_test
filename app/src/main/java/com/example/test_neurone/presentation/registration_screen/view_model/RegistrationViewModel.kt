package com.example.test_neurone.presentation.registration_screen.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test_neurone.core.components.ScreenStatus
import com.example.test_neurone.core.components.Validator
import com.example.test_neurone.domain.models.User
import com.example.test_neurone.domain.usecases.SaveUserUseCase
import com.example.test_neurone.presentation.registration_screen.state.RegistrationScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegistrationViewModel(
    private val validator: Validator,
    private val saveUserUseCase: SaveUserUseCase,

    ) : ViewModel() {

    private val _state = MutableStateFlow(RegistrationScreenState())
    val state: StateFlow<RegistrationScreenState> = _state.asStateFlow()



    // изменение номера
    fun onUserIdChange(value: String) {
        val filtered = validator.filterOnlyDigits(value)
        val validation = validator.validateUserId(filtered)

        _state.update { currentState ->
            currentState.copy(
                user = currentState.user.copy(userId = filtered),
                userIdValidation = validation
            )
        }
    }

    // изменение кода
    fun onCodeChange(value: String) {
        val filtered = validator.filterOnlyDigits(value)
        val validation = validator.validateCode(filtered)

        _state.update { currentState ->
            currentState.copy(
                user = currentState.user.copy(code = filtered),
                codeValidation = validation
            )
        }
    }


    // изменение имени
    fun onNameChange(value: String) {
        val filtered = validator.filterOnlyLatinLettersSingleSpace(value)
        val validation = validator.validateName(filtered)

        _state.update { currentState ->
            currentState.copy(
                user = currentState.user.copy(name = filtered),
                nameValidation = validation
            )
        }
    }

    // изменение фамилии
    fun onSurnameChange(value: String) {
        val filtered = validator.filterOnlyLatinLettersSingleSpace(value)
        val validation = validator.validateSurname(filtered)

        _state.update { currentState ->
            currentState.copy(
                user = currentState.user.copy(surname = filtered),
                surnameValidation = validation
            )
        }
    }

    // отправка формы
    fun submitForm(cont: () -> Unit) {
        if (_state.value.isFormValid) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    _state.update { it.copy(screenState = ScreenStatus.LOADING) }

                    // создаем нового пользователя с обрезанными пробелами
                    val trimmedUser = _state.value.user.copy(
                        name = _state.value.user.name.trim(),
                        surname = _state.value.user.surname.trim(),
                        phoneNumber = _state.value.user.phoneNumber.trim(),
                        email = _state.value.user.email.trim(),
                        userId = _state.value.user.userId.trim(),
                        code = _state.value.user.code.trim()
                    )

                    saveUserUseCase(trimmedUser)

                    withContext(Dispatchers.Main) {
                        cont()
                    }

                } catch (e: Exception) {
                    _state.update { it.copy(screenState = ScreenStatus.ERROR) }
                    Log.d("e", e.toString())
                }
            }
        }
    }
}


