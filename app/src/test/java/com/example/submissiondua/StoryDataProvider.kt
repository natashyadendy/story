package com.example.submissiondua

import com.example.submissiondua.data.response.ListStoryItem
import kotlin.random.Random

object StoryDataProvider {

    fun createDummyStoryList(): List<ListStoryItem> {
        val storyList = mutableListOf<ListStoryItem>()

        for (index in 1..100) {
            val story = ListStoryItem(
                id = index.toString(),
                name = "User $index",
                description = "This is the description for story number $index",
                createdAt = "2024-12-19",
                photoUrl = "https://example.com/photo${Random.nextInt(1, 100)}.jpg",
                lon = Random.nextDouble(0.0, 100.0),
                lat = Random.nextDouble(0.0, 100.0)
            )
            storyList.add(story)
        }

        return storyList
    }
}
