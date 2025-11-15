package com.example.test_neurone.core.components

import com.example.test_neurone.core.string_provider.StringProvider


//класс-валидатор, через композицию попадает в регистрацию
class Validator(private val stringProvider: StringProvider) {

    fun validateUserId(value: String): ValidationResult {
        return when {
            value.isEmpty() -> ValidationResult.Empty
            !value.all { it.isDigit() } -> ValidationResult.Error(stringProvider.getString("error_user_id_digits"))
            value.length != 16 -> ValidationResult.Error(stringProvider.getString("error_user_id_length"))
            else -> ValidationResult.Valid
        }
    }

    fun validateCode(value: String): ValidationResult {
        return when {
            value.isEmpty() -> ValidationResult.Empty
            !value.all { it.isDigit() } -> ValidationResult.Error(stringProvider.getString("error_code_digits"))
            value.length != 4 -> ValidationResult.Error(stringProvider.getString("error_code_length"))
            else -> ValidationResult.Valid
        }
    }

    fun validateName(value: String): ValidationResult {
        return when {
            value.isEmpty() -> ValidationResult.Empty
            !value.all { it.isLetter() && it.code < 128 || it == ' ' } -> ValidationResult.Error(stringProvider.getString("error_name_letters"))
            value.length < 2 -> ValidationResult.Error(stringProvider.getString("error_name_length"))
            else -> ValidationResult.Valid
        }
    }

    fun validateSurname(value: String): ValidationResult {
        return when {
            value.isEmpty() -> ValidationResult.Empty
            !value.all { it.isLetter() && it.code < 128 || it == ' ' } -> ValidationResult.Error(stringProvider.getString("error_surname_letters"))
            value.length < 2 -> ValidationResult.Error(stringProvider.getString("error_surname_length"))
            else -> ValidationResult.Valid
        }
    }

    fun filterOnlyDigits(value: String): String = value.filter { it.isDigit() }

    fun filterOnlyLatinLettersSingleSpace(value: String): String {
        val filtered = value.filter { it.isLetter() && it.code < 128 || it == ' ' }

        // заменяем несколько подряд идущих пробелов на один
        return filtered.replace(Regex(" +"), " ")
    }}

// ответ валидатора
sealed class ValidationResult {
    object Empty : ValidationResult()
    object Valid : ValidationResult()
    data class Error(val message: String) : ValidationResult()

    val isError: Boolean
        get() = this is Error

    val errorMessage: String
        get() = if (this is Error) message else ""
}
