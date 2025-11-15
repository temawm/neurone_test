package com.example.test_neurone.presentation.purchases_screen.state

import com.example.test_neurone.core.components.ScreenStatus
import com.example.test_neurone.domain.models.PurchaseGroup

data class PurchasesScreenState(
    val screenState: ScreenStatus = ScreenStatus.LOADING,
    val purchases: List<PurchaseGroup> = emptyList()
)