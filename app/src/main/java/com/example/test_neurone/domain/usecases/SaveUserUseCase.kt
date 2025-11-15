package com.example.test_neurone.domain.usecases

import com.example.test_neurone.domain.interfaces.UserRepository
import com.example.test_neurone.domain.models.User

class SaveUserUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(user: User) {
        userRepository.saveUser(user)
    }
}