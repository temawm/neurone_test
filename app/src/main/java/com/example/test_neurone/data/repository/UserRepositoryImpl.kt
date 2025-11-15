package com.example.test_neurone.data.repository

import com.example.test_neurone.data.local.interfaces.UserLocalDataSource
import com.example.test_neurone.domain.interfaces.UserRepository
import com.example.test_neurone.domain.models.User

class UserRepositoryImpl(
    private val localDataSource: UserLocalDataSource
) : UserRepository {

    override suspend fun saveUser(user: User) {
        localDataSource.saveUser(user)
    }

    override suspend fun getUser(): User? {
        return localDataSource.getUser()
    }

    override suspend fun clearUser() {
        localDataSource.clearUser()
    }
}