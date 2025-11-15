package com.example.test_neurone.presentation.purchases_screen.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test_neurone.core.components.ScreenStatus
import com.example.test_neurone.domain.usecases.GetPurchasesUseCase
import com.example.test_neurone.presentation.purchases_screen.state.PurchasesScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PurchasesViewModel(
    private val getPurchasesUseCase: GetPurchasesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(PurchasesScreenState())
    val state: StateFlow<PurchasesScreenState> = _state.asStateFlow()

    init {
        loadPurchases()
    }

    private fun loadPurchases() {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                _state.update { it.copy(screenState = ScreenStatus.LOADING) }

                val purchases = getPurchasesUseCase()

                _state.update {
                    it.copy(
                        purchases = purchases,
                        screenState = ScreenStatus.SUCCESS
                    )
                }

            } catch (e: Exception) {
                Log.e("PurchasesViewModel", "Ошибка получения покупок", e)
                _state.update {
                    it.copy(screenState = ScreenStatus.ERROR)
                }
            }
        }
    }
}