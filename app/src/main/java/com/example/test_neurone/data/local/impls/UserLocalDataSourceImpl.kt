package com.example.test_neurone.data.local.impls

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import com.example.test_neurone.data.local.interfaces.UserLocalDataSource
import com.example.test_neurone.domain.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserLocalDataSourceImpl(
    private val context: Context
) : UserLocalDataSource {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_USER_ID = "user_id"
        private const val KEY_CODE = "code"
        private const val KEY_NAME = "name"
        private const val KEY_SURNAME = "surname"
        private const val KEY_EMAIL = "email"
        private const val KEY_PHONE = "phone_number"
    }

    override suspend fun saveUser(user: User) {
        withContext(Dispatchers.IO) {
            prefs.edit {
                putString(KEY_USER_ID, user.userId)
                putString(KEY_CODE, user.code)
                putString(KEY_NAME, user.name)
                putString(KEY_SURNAME, user.surname)
                putString(KEY_EMAIL, user.email)
                putString(KEY_PHONE, user.phoneNumber)
            }
        }
    }

    override suspend fun getUser(): User? {
        return withContext(Dispatchers.IO) {
            val userId = prefs.getString(KEY_USER_ID, null)

            if (userId.isNullOrEmpty()) {
                return@withContext null
            }

            User(
                userId = userId,
                code = prefs.getString(KEY_CODE, "") ?: "",
                name = prefs.getString(KEY_NAME, "") ?: "",
                surname = prefs.getString(KEY_SURNAME, "") ?: "",
                email = prefs.getString(KEY_EMAIL, "") ?: "",
                phoneNumber = prefs.getString(KEY_PHONE, "") ?: ""
            )
        }
    }

    override suspend fun clearUser() {
        withContext(Dispatchers.IO) {
            prefs.edit {
                clear()
            }
        }
    }
}