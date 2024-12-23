package com.example.submissiondua.ui.story

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.submissiondua.CoroutineDispatcherRule
import com.example.submissiondua.repository.StoryDataRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest{
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    val mainDispatcherRule = CoroutineDispatcherRule()
    @Mock
    private lateinit var storyRepository: StoryDataRepository

}

