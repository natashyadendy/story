package com.example.submissiondua.ui.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.submissiondua.data.retrofit.ApiConfig
import com.example.submissiondua.pref.UserPreference
import com.example.submissiondua.data.response.ListStoryItem
import com.example.submissiondua.data.response.LoginResult
import com.example.submissiondua.data.response.StoryResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoryMapViewModel(private val pref: UserPreference) : ViewModel() {

    private val _stories = MutableLiveData<List<ListStoryItem>>()
    val storyItems: LiveData<List<ListStoryItem>> = _stories

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun fetchStories(token: String, location: Int = 1) {
        _isLoading.value = true
        val client = ApiConfig.provideApiService().getStoriesWithLocation(token, location)
        client.enqueue(object : Callback<StoryResponse> {
            override fun onResponse(call: Call<StoryResponse>, response: Response<StoryResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    responseBody?.listStory?.let {
                        _stories.value = it
                    } ?: run {
                        _errorMessage.value = "No stories found."
                    }
                } else {
                    _errorMessage.value = "Failed to load stories."
                }
            }

            override fun onFailure(call: Call<StoryResponse>, t: Throwable) {
                _isLoading.value = false
                _errorMessage.value = "Network error: ${t.message}"
            }
        })
    }

    fun getUser(): LiveData<LoginResult> {
        return pref.getUserInfo().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            pref.clearUserInfo()
        }
    }

    companion object {
        private const val TAG = "MapsViewModel"
    }
}
