package com.example.test_neurone.presentation.home_screen.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test_neurone.core.components.ScreenStatus
import com.example.test_neurone.domain.models.User
import com.example.test_neurone.domain.usecases.GetUserUseCase
import com.example.test_neurone.presentation.home_screen.state.HomeScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getUserUseCase: GetUserUseCase,
): ViewModel() {


    //главный стейт
    private val _state = MutableStateFlow(HomeScreenState())
    val state: StateFlow<HomeScreenState> = _state.asStateFlow()


    // меняет значение свитча в блоке биометрии
    fun changeBiometryAccess() {
        _state.update { current ->
            current.copy(
                isActivatedBiometryEntry = !current.isActivatedBiometryEntry
            )
        }
    }

    // при повторном заходе на экран тянуть данные (public обертка над loadUser())
    fun onScreenResume() {
        loadUser()
    }


    // стучимся в sharedpref через юзкейс
    private fun loadUser() {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                _state.update { it.copy(screenState = ScreenStatus.LOADING) }

                val user = getUserUseCase()

                _state.update {
                    it.copy(
                        user = user ?: User(),
                        screenState = ScreenStatus.SUCCESS
                    )
                }

            } catch (e: Exception) {
                _state.update {
                    it.copy(screenState = ScreenStatus.ERROR)
                }
            }
        }
    }


}