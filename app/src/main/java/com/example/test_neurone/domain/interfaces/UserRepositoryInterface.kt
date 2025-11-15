package com.example.test_neurone.domain.interfaces

import com.example.test_neurone.domain.models.User

interface UserRepository {
    suspend fun saveUser(user: User)
    suspend fun getUser(): User?
    suspend fun clearUser()
}