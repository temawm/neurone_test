package com.example.test_neurone.presentation.registration_screen.state

import com.example.test_neurone.core.components.ScreenStatus
import com.example.test_neurone.core.components.ValidationResult
import com.example.test_neurone.domain.models.User

data class RegistrationScreenState(
    var screenState: ScreenStatus = ScreenStatus.SUCCESS,
    val user: User = User(),
    val userIdValidation: ValidationResult = ValidationResult.Empty,
    val codeValidation: ValidationResult = ValidationResult.Empty,
    val nameValidation: ValidationResult = ValidationResult.Empty,
    val surnameValidation: ValidationResult = ValidationResult.Empty
) {
    val isFormValid: Boolean
        get() = userIdValidation is ValidationResult.Valid &&
                codeValidation is ValidationResult.Valid &&
                nameValidation is ValidationResult.Valid &&
                surnameValidation is ValidationResult.Valid
}
