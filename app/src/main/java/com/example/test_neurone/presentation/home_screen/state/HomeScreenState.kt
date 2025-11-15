package com.example.test_neurone.presentation.home_screen.state

import com.example.test_neurone.core.components.ScreenStatus
import com.example.test_neurone.domain.models.User



// стейт для экрана HomeScreen (Главный экран)
data class HomeScreenState (
    var screenState: ScreenStatus = ScreenStatus.SUCCESS,
    var isEmailConfirmed: Boolean = false,
    var user: User = User(),
    var language: String = "русский",
    var isActivatedBiometryEntry: Boolean = false
)