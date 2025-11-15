package com.example.test_neurone.data.local.interfaces

import com.example.test_neurone.domain.models.User

interface UserLocalDataSource {
    suspend fun saveUser(user: User)
    suspend fun getUser(): User?
    suspend fun clearUser()
}