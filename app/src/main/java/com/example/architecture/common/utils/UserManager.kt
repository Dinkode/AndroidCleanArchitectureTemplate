package com.example.architecture.common.utils

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.architecture.di.dataStore
import com.example.architecture.login.domain.models.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserManager(private val context: Context) {
    companion object {
        private val USER_KEY = stringPreferencesKey("user")
    }

    fun getUser(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[USER_KEY]
        }
    }

    suspend fun saveUser(user: UserModel) {
        context.dataStore.edit { preferences ->
            preferences[USER_KEY] = user.toString()
        }
    }

    suspend fun deleteUser() {
        context.dataStore.edit { preferences ->
            preferences.remove(USER_KEY)
        }
    }
}