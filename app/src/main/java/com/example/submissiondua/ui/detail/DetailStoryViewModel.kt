package com.example.submissiondua.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.submissiondua.pref.UserPreference
import com.example.submissiondua.data.response.DetailStoryResponse
import com.example.submissiondua.data.response.LoginResult
import com.example.submissiondua.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailStoryViewModel(private val pref: UserPreference) : ViewModel() {

    private val _detailName = MutableLiveData<String>()
    val detailName: LiveData<String> = _detailName

    private val _detailDesc = MutableLiveData<String>()
    val detailDesc: LiveData<String> = _detailDesc

    private val _detailPhotoUrl = MutableLiveData<String>()
    val detailPhotoUrl: LiveData<String> = _detailPhotoUrl

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _statusMessage = MutableLiveData<String>()
    val statusMessage: LiveData<String> = _statusMessage

    fun getDetail(token: String, id: String) {
        _isLoading.value = true
        val client = ApiConfig.provideApiService().getDetailStory(token, id)

        client.enqueue(object : Callback<DetailStoryResponse> {
            override fun onResponse(
                call: Call<DetailStoryResponse>,
                response: Response<DetailStoryResponse>
            ) {
                _isLoading.value = false

                if (response.isSuccessful) {
                    val story = response.body()?.story
                    story?.let {
                        _detailName.value = it.name
                        _detailDesc.value = it.description
                        _detailPhotoUrl.value = it.photoUrl
                    } ?: run {
                        _statusMessage.value = "Story not found"
                    }
                } else {
                    _statusMessage.value = "Failed to load details: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<DetailStoryResponse>, t: Throwable) {
                _isLoading.value = false
                _statusMessage.value = "Error: ${t.message}"
            }
        })
    }

    fun getUser(): LiveData<LoginResult> {
        return pref.getUserInfo().asLiveData()
    }

    companion object {
        private const val TAG = "DetailStoryViewModel"
    }
}
