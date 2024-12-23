package com.example.submissiondua.pref

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.submissiondua.data.response.LoginResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {

    fun getUserInfo(): Flow<LoginResult> = dataStore.data.map { preferences ->
        LoginResult(
            preferences[USER_NAME_KEY] ?: "",
            preferences[USER_ID_KEY] ?: "",
            preferences[USER_TOKEN_KEY] ?: ""
        )
    }

    suspend fun saveUserInfo(name: String, userId: String, token: String) {
        dataStore.edit { preferences ->
            preferences[USER_NAME_KEY] = name
            preferences[USER_ID_KEY] = userId
            preferences[USER_TOKEN_KEY] = token
        }
    }

    suspend fun clearUserInfo() {
        dataStore.edit { preferences ->
            preferences[USER_NAME_KEY] = ""
            preferences[USER_ID_KEY] = ""
            preferences[USER_TOKEN_KEY] = ""
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        private val USER_NAME_KEY = stringPreferencesKey("name")
        private val USER_ID_KEY = stringPreferencesKey("userId")
        private val USER_TOKEN_KEY = stringPreferencesKey("token")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}
