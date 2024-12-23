package com.example.submissiondua.ui.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submissiondua.R
import com.example.submissiondua.pref.UserPreference
import com.example.submissiondua.data.response.LoginResponse
import com.example.submissiondua.data.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val pref: UserPreference, private val app: Application) : ViewModel() {

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName

    private val _userId = MutableLiveData<String>()
    val userId: LiveData<String> = _userId

    private val _userToken = MutableLiveData<String>()
    val userToken: LiveData<String> = _userToken

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _statusMessage = MutableLiveData<String?>()
    val statusMessage: LiveData<String?> = _statusMessage

    fun clearStatusMessage() {
        _statusMessage.value = null
    }

    fun login(email: String, password: String) {
        _isLoading.value = true
        val client = ApiConfig.provideApiService().login(email, password)

        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && responseBody.loginResult != null) {
                        _userName.value = responseBody.loginResult?.name.orEmpty()
                        _userId.value = responseBody.loginResult?.userId.orEmpty()
                        _userToken.value = responseBody.loginResult?.token.orEmpty()

                        saveUserData(responseBody.loginResult?.name.orEmpty(),
                            responseBody.loginResult?.userId.orEmpty(),
                            responseBody.loginResult?.token.orEmpty())

                        _statusMessage.value = app.getString(R.string.login_successful)
                        Log.d(TAG, "Token: ${_userToken.value}")
                    } else {
                        _statusMessage.value = app.getString(R.string.login_failed_please_ensure_the_email_and_password_are_valid)
                    }
                } else {
                    _statusMessage.value = app.getString(R.string.login_failed_please_ensure_the_email_and_password_are_valid)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isLoading.value = false
                _statusMessage.value = "Error: ${t.localizedMessage ?: t.message}"
            }
        })
    }

    private fun saveUserData(name: String, userId: String, token: String) {
        viewModelScope.launch {
            pref.saveUserInfo(name, userId, "Bearer $token")
        }
    }

    companion object {
        private const val TAG = "LoginViewModel"
    }
}
