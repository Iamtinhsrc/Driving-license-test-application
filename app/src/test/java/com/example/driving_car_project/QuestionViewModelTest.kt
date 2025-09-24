package com.example.driving_car_project

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.driving_car_project.data.model.Question
import com.example.driving_car_project.data.source.Repository
import com.example.driving_car_project.ui.viewmodel.QuestionUiState
import com.example.driving_car_project.ui.viewmodel.QuestionViewModel
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.same

@OptIn(ExperimentalCoroutinesApi::class)
class QuestionViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val dispatcher = StandardTestDispatcher()
    private lateinit var repository: Repository
    private lateinit var viewModel: QuestionViewModel

    @Before
    fun setup(){
        Dispatchers.setMain(dispatcher)
        repository = mockk()
        viewModel = QuestionViewModel(repository, dispatcher)
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun loadLocalOrRemoteQuestions_successFromLocal() = runTest {

        val questions = listOf(
            Question(1, "Cau 1", mockk(), mockk(), "A", null, 2003, false),
            Question(2, "Cau 2", mockk(), mockk(), "C", null, 2006, true)

        )
        coEvery { repository.getLocalQuestions() } returns questions

        viewModel.loadLocalOrRemoteQuestions()
        dispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.first()
        assertTrue(state is QuestionUiState.Success)
        assertEquals(2, (state as QuestionUiState.Success).questions.size)
    }

    @Test
    fun loadByType_successCritical() = runTest {

        val criticals = listOf(
            Question(10, "Cau diem liet", mockk(), mockk(),"C", null, 2001, true)
        )
        coEvery { repository.getCriticalQuestions() } returns criticals

        viewModel.loadByType(2001)
        dispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.first()
        assertTrue(state is QuestionUiState.Success)
        assertEquals("Cau diem liet", (state as QuestionUiState.Success).questions.first().question)
    }
}