package com.example.test_neurone.domain.usecases

import com.example.test_neurone.domain.interfaces.UserRepository
import com.example.test_neurone.domain.models.User

class GetUserUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): User? {
        return userRepository.getUser()
    }
}