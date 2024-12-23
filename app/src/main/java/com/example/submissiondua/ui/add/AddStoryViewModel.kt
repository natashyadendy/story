package com.example.submissiondua.ui.add

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.submissiondua.R
import com.example.submissiondua.pref.UserPreference
import com.example.submissiondua.data.response.AddStoryResponse
import com.example.submissiondua.data.response.LoginResult
import com.example.submissiondua.data.retrofit.ApiConfig
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddStoryViewModel(private val pref: UserPreference, private val app: Application) : ViewModel() {

    private val _isUploadSuccess = MutableLiveData<Boolean>()
    val isUploadSuccess: LiveData<Boolean> get() = _isUploadSuccess

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _statusMessage = MutableLiveData<String?>()
    val statusMessage: LiveData<String?> get() = _statusMessage

    fun clearStatusMessage() {
        _statusMessage.value = null
    }

    fun getUser(): LiveData<LoginResult> = pref.getUserInfo().asLiveData()

    fun uploadStoryContent(token: String, image: MultipartBody.Part, description: RequestBody) {
        _isLoading.postValue(true)
        ApiConfig.provideApiService().uploadStory(token, image, description).enqueue(object : Callback<AddStoryResponse> {
            override fun onResponse(
                call: Call<AddStoryResponse>,
                response: Response<AddStoryResponse>
            ) {
                _isLoading.postValue(false)

                if (response.isSuccessful) {
                    _isUploadSuccess.value = true
                    _statusMessage.value = app.getString(R.string.add_story_success)
                } else {
                    _isUploadSuccess.value = false
                    _statusMessage.value = app.getString(R.string.failed_to_add_story, response.message())
                }
            }

            override fun onFailure(call: Call<AddStoryResponse>, t: Throwable) {
                _isLoading.postValue(false)
                _isUploadSuccess.postValue(false)
                _statusMessage.value = app.getString(R.string.error, t.localizedMessage ?: "Unknown error")
            }
        })
    }

    companion object {
        private const val TAG = "UploadStoryViewModel"
    }
}
