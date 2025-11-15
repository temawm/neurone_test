package com.example.test_neurone.core.components


// класс валидатор, внедрим через композицию
class Validator {

    fun validateUserId(value: String): ValidationResult {
        return when {
            value.isEmpty() -> ValidationResult.Empty
            !value.all { it.isDigit() } -> ValidationResult.Error("Номер должен содержать только цифры")
            value.length != 16 -> ValidationResult.Error("Номер должен содержать 16 цифр")
            else -> ValidationResult.Valid
        }
    }

    fun validateCode(value: String): ValidationResult {
        return when {
            value.isEmpty() -> ValidationResult.Empty
            !value.all { it.isDigit() } -> ValidationResult.Error("Код должен содержать только цифры")
            value.length != 4 -> ValidationResult.Error("Код должен содержать 4 цифры")
            else -> ValidationResult.Valid
        }
    }

    fun validateName(value: String): ValidationResult {
        return when {
            value.isEmpty() -> ValidationResult.Empty
            !value.all { it.isLetter() && it.code < 128 } -> ValidationResult.Error("Имя должно содержать только латинские буквы")
            value.length < 2 -> ValidationResult.Error("Имя должно содержать минимум 2 буквы")
            else -> ValidationResult.Valid
        }
    }

    fun validateSurname(value: String): ValidationResult {
        return when {
            value.isEmpty() -> ValidationResult.Empty
            !value.all { it.isLetter() && it.code < 128 } -> ValidationResult.Error("Фамилия должна содержать только латинские буквы")
            value.length < 2 -> ValidationResult.Error("Фамилия должна содержать минимум 2 буквы")
            else -> ValidationResult.Valid
        }
    }

    fun filterOnlyDigits(value: String): String {
        return value.filter { it.isDigit() }
    }

    fun filterOnlyLatinLetters(value: String): String {
        return value.filter { it.isLetter() && it.code < 128 }
    }
}


// ответ валидатора
sealed class ValidationResult {
    data object Empty : ValidationResult()
    data object Valid : ValidationResult()
    data class Error(val message: String) : ValidationResult()

    val isError: Boolean
        get() = this is Error

    val errorMessage: String
        get() = if (this is Error) message else ""
}