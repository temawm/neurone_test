package com.example.test_neurone.domain.usecases

import com.example.test_neurone.domain.interfaces.UserRepository

class ClearUserUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke() {
        userRepository.clearUser()
    }
}