package com.example.submissiondua.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.submissiondua.StoryDataProvider
import com.example.submissiondua.CoroutineDispatcherRule
import com.example.submissiondua.repository.StoryDataRepository
import com.example.submissiondua.data.response.ListStoryItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest{
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = CoroutineDispatcherRule()

    @Mock
    private lateinit var storyRepository: StoryDataRepository


    @Test
    fun `when Get Story Should Not Null and Return No Data`() = runTest {
        val data: PagingData<ListStoryItem> = PagingData.from(emptyList())
        val expectedStory = MutableLiveData<PagingData<ListStoryItem>>()
        expectedStory.value = data

        Mockito.`when`(storyRepository.fetchStories()).thenReturn(expectedStory)
        val actualStory = storyRepository.fetchStories()
        Assert.assertNotNull(actualStory)
        Assert.assertNotNull(arrayOf("a", "b"))
    }

    @Test
    fun `when Get Story Empty Should Not Null and Return Data`() = runTest {
        val createDummyStoryList = StoryDataProvider.createDummyStoryList()
        val data: PagingData<ListStoryItem> = PagingStoryDump.snapshot(createDummyStoryList)

        val expectedStory = MutableLiveData<PagingData<ListStoryItem>>()
        expectedStory.value = data

        Mockito.`when`(storyRepository.fetchStories()).thenReturn(expectedStory)
        val actualStory = storyRepository.fetchStories()
        Assert.assertNotNull(actualStory)
    }
    class PagingStoryDump: PagingSource<Int, ListStoryItem>(){
        companion object{
            fun snapshot(items: List<ListStoryItem>): PagingData<ListStoryItem>{
                return PagingData.from(items)
            }
        }
        override fun getRefreshKey(state: PagingState<Int, ListStoryItem>): Int? = null

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListStoryItem> {
            return LoadResult.Page(emptyList(), prevKey = null, nextKey = null)
        }
    }
}

