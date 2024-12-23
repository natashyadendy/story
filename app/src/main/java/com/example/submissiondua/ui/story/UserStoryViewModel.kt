package com.example.submissiondua.ui.story

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.submissiondua.data.response.ListStoryItem
import com.example.submissiondua.pref.UserPreference
import com.example.submissiondua.repository.StoryDataRepository
import kotlinx.coroutines.launch

class UserStoryViewModel(
    private val storyRepository: StoryDataRepository,
    private val pref: UserPreference
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val story: LiveData<PagingData<ListStoryItem>> by lazy {
        storyRepository.fetchStories().cachedIn(viewModelScope)
    }

    init {
        checkUserStatus()
    }

    private fun checkUserStatus() {
        viewModelScope.launch {
            val user = pref.getUserInfo().asLiveData().value
            if (user == null) {
            }
        }
    }

    fun loadStories() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                storyRepository.fetchStories()
            } catch (exception: Exception) {
                handleError(exception)
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun handleError(exception: Exception) {
    }
}