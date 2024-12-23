package com.example.submissiondua.ui

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.submissiondua.pref.UserPreference
import com.example.submissiondua.repository.StoryDataRepository
import com.example.submissiondua.data.response.ListStoryItem
import com.example.submissiondua.data.response.LoginResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class MainViewModel(
    private val storyRepository: StoryDataRepository,
    private val userPreference: UserPreference
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _logoutStatus = MutableLiveData<Boolean>()
    val logoutStatus: LiveData<Boolean> get() = _logoutStatus

    val story: LiveData<PagingData<ListStoryItem>> =
        storyRepository.fetchStories().cachedIn(viewModelScope)


    fun getUser(): LiveData<LoginResult> {
        return userPreference.getUserInfo().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            userPreference.clearUserInfo()
            _logoutStatus.postValue(true)
        }
    }


    fun setLoading(isLoading: Boolean) {
        _isLoading.postValue(isLoading)
    }
}
