//package com.example.driving_car_project
//
//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import com.example.driving_car_project.model.Exam
//import com.example.driving_car_project.model.Question
//import com.example.driving_car_project.Repository
//import com.example.driving_car_project.exam.ExamSessionResult
//import com.example.driving_car_project.exam.ExamViewModel
//import io.mockk.coEvery
//import io.mockk.coVerify
//import io.mockk.mockk
//import junit.framework.TestCase.assertEquals
//import junit.framework.TestCase.assertTrue
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.first
//import kotlinx.coroutines.test.StandardTestDispatcher
//import kotlinx.coroutines.test.resetMain
//import kotlinx.coroutines.test.runTest
//import kotlinx.coroutines.test.setMain
//import org.junit.After
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//
//@OptIn(ExperimentalCoroutinesApi::class)
//class ExamViewModelTest {
//
//    @get:Rule
//    val instantExecutorRule = InstantTaskExecutorRule()
//
//    private val dispatcher = StandardTestDispatcher()
//    private lateinit var repository: com.example.driving_car_project.Repository
//    private lateinit var viewModel: ExamViewModel
//
//    @Before
//    fun setup() {
//        Dispatchers.setMain(dispatcher)
//        repository = mockk(relaxed = true)
//        viewModel = ExamViewModel(
//            repository,
//            ioDispatcher = dispatcher,
//            defaultDispatcher = dispatcher,
//            mainDispatcher = dispatcher
//        )
//    }
//
//    @After
//    fun tearDown(){
//        Dispatchers.resetMain()
//    }
//
//    @Test
//    fun submitExam_allCorrect() = runTest {
//        val exam = Exam(id = 1, title = "De test", questionIds = listOf(1, 2))
//        val questions = listOf(
//            Question(1, "Cau 1", mockk(), mockk(), "A", null, 2002, false),
//            Question(2, "Cau 2", mockk(), mockk(), "C", null, 2006, true)
//        )
//
//        coEvery { repository.getAnswersByExamId(1) } returns emptyList()
//        coEvery { repository.getLocalQuestions() } returns questions
//
//        // Inject exam + questions + answers
//        (viewModel.javaClass.getDeclaredField("_currentExam").apply {
//            isAccessible = true
//        }.get(viewModel) as MutableStateFlow<Exam?>).value = exam
//
//        (viewModel.javaClass.getDeclaredField("_questions").apply {
//            isAccessible = true
//        }.get(viewModel) as MutableStateFlow<List<Question>>).value = questions
//
//        (viewModel.javaClass.getDeclaredField("_selectedAnswers").apply {
//            isAccessible = true
//        }.get(viewModel) as MutableStateFlow<Map<Int, String>>).value = mapOf(1 to "A", 2 to "C")
//
//        viewModel.submitExam()
//        dispatcher.scheduler.advanceUntilIdle()
//
//        val result: ExamSessionResult = viewModel.resultEvents.first()
//        assertEquals(2, result.correct)
//        assertEquals(2, result.total)
//        assertTrue(result.passed)
//        coVerify { repository.saveExamHistory(any()) }
//    }
//
//
//    @Test
//    fun submitExam_someWrong() = runTest {
//        val exam = Exam(id = 2, title = "Đề 2", questionIds = listOf(1, 2))
//        val questions = listOf(
//            Question(1, "Q1", mockk(), mockk(), "A", null, 2001, false),
//            Question(2, "Q2", mockk(), mockk(), "B", null, 2001, false)
//        )
//
//        coEvery { repository.getAnswersByExamId(2) } returns emptyList()
//
//        // Inject exam + questions + answers
//        (viewModel.javaClass.getDeclaredField("_currentExam").apply {
//            isAccessible = true
//        }.get(viewModel) as MutableStateFlow<Exam?>).value = exam
//
//        (viewModel.javaClass.getDeclaredField("_questions").apply {
//            isAccessible = true
//        }.get(viewModel) as MutableStateFlow<List<Question>>).value = questions
//
//        (viewModel.javaClass.getDeclaredField("_selectedAnswers").apply {
//            isAccessible = true
//        }.get(viewModel) as MutableStateFlow<Map<Int, String>>).value = mapOf(1 to "A", 2 to "D")
//
//        viewModel.submitExam()
//        dispatcher.scheduler.advanceUntilIdle()
//
//        val result: ExamSessionResult = viewModel.resultEvents.first()
//        assertEquals(1, result.correct)
//        assertEquals(2, result.total)
//        assertTrue(!result.passed) // < 80%
//    }
//
//}