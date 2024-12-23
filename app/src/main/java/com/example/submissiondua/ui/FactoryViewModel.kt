package com.example.submissiondua.ui

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.submissiondua.di.DependencyInjector
import com.example.submissiondua.pref.UserPreference
import com.example.submissiondua.repository.StoryDataRepository
import com.example.submissiondua.ui.add.AddStoryViewModel
import com.example.submissiondua.ui.detail.DetailStoryViewModel
import com.example.submissiondua.ui.login.LoginViewModel
import com.example.submissiondua.ui.maps.StoryMapViewModel
import com.example.submissiondua.ui.story.UserStoryViewModel

class FactoryViewModel private constructor(
    private val storyRepository: StoryDataRepository,
    private val userPreference: UserPreference,
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return createViewModel(modelClass)
    }

    private fun <T : ViewModel> createViewModel(modelClass: Class<T>): T {
        return when (modelClass) {
            MainViewModel::class.java -> MainViewModel(storyRepository, userPreference) as T
            UserStoryViewModel::class.java -> UserStoryViewModel(storyRepository, userPreference) as T
            LoginViewModel::class.java -> LoginViewModel(userPreference, application) as T
            DetailStoryViewModel::class.java -> DetailStoryViewModel(userPreference) as T
            AddStoryViewModel::class.java -> AddStoryViewModel(userPreference, application) as T
            StoryMapViewModel::class.java -> StoryMapViewModel(userPreference) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

    companion object {
        @Volatile
        private var instance: FactoryViewModel? = null


        fun getInstance(context: Context): FactoryViewModel {
            return instance ?: synchronized(this) {
                instance ?: buildFactory(context).also { instance = it }
            }
        }

        private fun buildFactory(context: Context): FactoryViewModel {
            return FactoryViewModel(
                DependencyInjector.provideStoryRepository(context),
                DependencyInjector.provideUserPreferences(context),
                context.applicationContext as Application
            )
        }
    }
}
