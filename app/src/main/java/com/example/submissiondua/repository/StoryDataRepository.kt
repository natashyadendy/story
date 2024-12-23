package com.example.submissiondua.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.submissiondua.pref.StoryPagingSource
import com.example.submissiondua.pref.UserPreference
import com.example.submissiondua.data.response.ListStoryItem
import com.example.submissiondua.data.retrofit.ApiService

class StoryDataRepository(private val apiService: ApiService, private val pref: UserPreference) {

    fun fetchStories(pageSize: Int = DEFAULT_PAGE_SIZE): LiveData<PagingData<ListStoryItem>> {
        return Pager(
            config = createPagingConfiguration(pageSize),
            pagingSourceFactory = {
                StoryPagingSource(apiService, pref)
            }
        ).liveData
    }

    private fun createPagingConfiguration(pageSize: Int): PagingConfig {
        return PagingConfig(
            pageSize = pageSize,
            enablePlaceholders = false,
            prefetchDistance = 2,
            initialLoadSize = pageSize * 2
        )
    }

    companion object {
        private const val DEFAULT_PAGE_SIZE = 10
    }
}
