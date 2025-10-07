package com.example.driving_car_project


import com.example.driving_car_project.model.Question
import com.example.driving_car_project.exam.ExamSessionResult
import com.example.driving_car_project.exam.ExamViewModel
import com.example.driving_car_project.model.Image
import com.example.driving_car_project.model.Option
import com.example.driving_car_project.model.QuestionResponse
import com.example.driving_car_project.remote.ResponseResult
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@ExperimentalCoroutinesApi
class ExamViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var repository: Repository
    private lateinit var viewModel: ExamViewModel
    private lateinit var dispatcher: TestDispatcher


    @Before
    fun setup() {
        repository = mockk()
        dispatcher = StandardTestDispatcher()
        Dispatchers.setMain(dispatcher)

        viewModel = ExamViewModel(
            repository,
            ioDispatcher = Dispatchers.Unconfined,
            defaultDispatcher = Dispatchers.Unconfined,
            mainDispatcher = Dispatchers.Unconfined
        )
    }

    @Test
    fun `createAndStartExam should create exam with correct number of questions`() = runTest {
        val questions = (1..30).map { i ->
            Question(
                id = i,
                question = "Q$i",
                option = Option(a = "A", b = "B", c = "C", d = "D", e = null),
                image = Image(img1 = null, img2 = null, img3 = null, img4 = null),
                answer = "A",
                suggest = null,
                questionType = 1,
                critical = false
            )
        }

        coEvery { repository.getLocalQuestions() } returns questions
        coEvery { repository.saveExam(any()) } returns 1L
        coEvery { repository.fetchQuestions() } returns ResponseResult.Success(QuestionResponse(emptyList()))


        viewModel.createAndStartExam(10, "Test Exam")

        assertEquals(10, viewModel.questions.value.size)
        assertEquals("Test Exam", viewModel.currentExam.value?.title)
        assertEquals(0, viewModel.currentIndex.value)
        assertEquals(300, viewModel.examTimeLeft.value)
    }

    @Test
    fun `chooseAnswer should update selectedAnswers and save to repository`() = runTest {
        val questions = listOf(
            Question(
                id = 1,
                question = "Q1",
                option = Option(a = "A", b = "B", c = "C", d = "D", e = null),
                image = Image(img1 = null, img2 = null, img3 = null, img4 = null),
                answer = "A",
                suggest = null,
                questionType = 1,
                critical = false
            )
        )

        coEvery { repository.getLocalQuestions() } returns questions
        coEvery { repository.saveExam(any()) } returns 1L
        coEvery { repository.saveAnswers(any()) } just Runs
        coEvery { repository.getAnswersByExamId(any()) } returns emptyList()
        coEvery { repository.saveExamHistory(any()) } returns 1L


        viewModel = ExamViewModel(repository, dispatcher, dispatcher, dispatcher)

        viewModel.createAndStartExam(1)
        advanceUntilIdle()

        viewModel.chooseAnswer(1, "A")
        advanceUntilIdle() // đảm bảo coroutine trong chooseAnswer chạy

        coVerify(exactly = 1) {
            repository.saveAnswers(match { it.first().selectedAnswer == "A" })
        }

        assertEquals("A", viewModel.selectedAnswers.value[1])
    }




    @Test
    fun `submitExam should emit resultEvent with correct score`() = runTest {
        val q = (1..1).map { i ->
            Question(
                id = i,
                question = "Q$i",
                option = Option(a = "A", b = "B", c = "C", d = "D", e = null),
                image = Image(img1 = null, img2 = null, img3 = null, img4 = null),
                answer = "A",
                suggest = null,
                questionType = 1,
                critical = false
            )
        }

        // Mock dữ liệu repo
        coEvery { repository.getLocalQuestions() } returns q
        coEvery { repository.saveExam(any()) } returns 123L // fix examId thật
        coEvery { repository.saveAnswers(any()) } just Runs
        coEvery { repository.saveExamHistory(any()) } returns 1L

        // Trả về đáp án với examId = 123L khớp với saveExam()
        coEvery { repository.getAnswersByExamId(123) } returns listOf(
            com.example.driving_car_project.model.Answer(
                examId = 123,
                questionId = 1,
                selectedAnswer = "A",
                isCorrect = true
            )
        )

        // Khi tạo đề, examId trong ViewModel nên = 123
        viewModel.createAndStartExam(1)
        viewModel.chooseAnswer(1, "A")

        val result = mutableListOf<ExamSessionResult>()
        val job = launch { viewModel.resultEvents.collect { result.add(it) } }

        viewModel.submitExam()
        advanceUntilIdle()

        // Kết quả đúng
        assertEquals(1, result.size)
        assertEquals(1, result.first().correct)

        job.cancel()
    }

}

@ExperimentalCoroutinesApi
class MainDispatcherRule(
    private val dispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : TestWatcher() {

    override fun starting(description: Description) {
        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}

