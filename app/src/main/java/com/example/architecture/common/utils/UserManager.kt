package com.example.architecture.common.utils

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.architecture.di.dataStore
import com.example.architecture.login.domain.models.UserModel
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.converter.moshi.MoshiConverterFactory

class UserManager(private val context: Context) {
    companion object {
        private val USER_KEY = stringPreferencesKey("user")
    }

    val moshi = Moshi.Builder()
        .build()

    fun getUser(): Flow<UserModel?> {
        return context.dataStore.data.map { preferences ->

            preferences[USER_KEY]?.let { moshi.adapter(UserModel::class.java).fromJson(it) }

        }
    }

    suspend fun saveUser(user: UserModel) {
        context.dataStore.edit { preferences ->
            preferences[USER_KEY] = moshi.adapter(UserModel::class.java).toJson(user)
        }
    }

    suspend fun deleteUser() {
        context.dataStore.edit { preferences ->
            preferences.remove(USER_KEY)
        }
    }
}