package com.example.submissiondua.di

import android.content.Context
import com.example.submissiondua.pref.UserPreference
import com.example.submissiondua.repository.StoryDataRepository
import com.example.submissiondua.data.retrofit.ApiConfig
import com.example.submissiondua.ui.dataStore

object DependencyInjector {
    fun provideStoryRepository(context: Context): StoryDataRepository {
        val apiService = ApiConfig.provideApiService()
        val pref = UserPreference.getInstance(context.dataStore)
        return StoryDataRepository(apiService, pref)
    }

    fun provideUserPreferences(context: Context): UserPreference {
        return UserPreference.getInstance(context.dataStore)
    }
}
