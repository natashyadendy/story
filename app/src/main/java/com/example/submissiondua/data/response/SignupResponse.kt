package com.example.submissiondua.data.response

import com.google.gson.annotations.SerializedName

data class SignupResponse(

	@SerializedName("error")
	val error: Boolean,

	@SerializedName("message")
	val message: String
)
