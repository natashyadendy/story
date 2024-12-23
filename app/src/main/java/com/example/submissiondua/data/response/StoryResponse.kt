package com.example.submissiondua.data.response

import com.google.gson.annotations.SerializedName

data class StoryResponse(

	@SerializedName("listStory")
	val listStory: List<ListStoryItem> = emptyList(),

	@SerializedName("error")
	val error: Boolean,

	@SerializedName("message")
	val message: String
)

data class ListStoryItem(

	@SerializedName("photoUrl")
	val photoUrl: String,

	@SerializedName("createdAt")
	val createdAt: String,

	@SerializedName("name")
	val name: String,

	@SerializedName("description")
	val description: String,

	@SerializedName("lon")
	val lon: Double? = null,

	@SerializedName("id")
	val id: String,

	@SerializedName("lat")
	val lat: Double? = null
)
