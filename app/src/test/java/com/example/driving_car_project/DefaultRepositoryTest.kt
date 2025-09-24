package com.example.driving_car_project


import com.example.driving_car_project.data.model.Question
import com.example.driving_car_project.data.model.QuestionResponse
import com.example.driving_car_project.data.model.QuestionType
import com.example.driving_car_project.data.model.QuestionTypeResponse
import com.example.driving_car_project.data.source.DataSource
import com.example.driving_car_project.data.source.DefaultRepository
import com.example.driving_car_project.data.source.remote.ResponseResult
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class DefaultRepositoryTest {

    private lateinit var localDataSource: DataSource.LocalDataSource
    private lateinit var remoteDataSource: DataSource.RemoteDataSource
    private lateinit var repository: DefaultRepository

    @Before
    fun setup() {
        localDataSource = mockk(relaxed = true)
        remoteDataSource = mockk()
        repository = DefaultRepository(localDataSource, remoteDataSource)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    //1. Test fetchQuestions success tu Remote, luu xuong Local
    @Test
    fun repository_fetchQuestions_remoteSuccess_dataSavedToLocal() = runTest {

        val questions = listOf(
            Question(1, "Cau 1", option = mockk(), image = mockk(), "A", null, 2002, false),
            Question(2, "Cau 2", option = mockk(), image = mockk(), "C", null, 2003, true)
        )
        val response = QuestionResponse(questions)
        coEvery { remoteDataSource.getQuestions() } returns ResponseResult.Success(response)

        val result = repository.fetchQuestions()

        assertTrue(result is ResponseResult.Success)
        coVerifyOrder {
            localDataSource.clearQuestions()
            localDataSource.insertQuestions(questions)
        }
    }

    //2. Test fetchQuestions fail tu Remote, khong luu vao DB
    @Test
    fun repository_fetchQuestions_remoteError_dataNotSavedToLocal() = runTest {

        coEvery { remoteDataSource.getQuestions() } returns ResponseResult.Error("Network error")

        val result = repository.fetchQuestions()

        assertTrue(result is ResponseResult.Error)
        coVerify(exactly = 0) { localDataSource.insertAnswers(any())}
    }

    //3. Test lay du lieu tu Local khi Offline
    @Test
    fun repository_getLocalQuestions_offline_dataReturned() = runTest {

        val questions = listOf(
            Question(1, "Offline 1", mockk(), mockk(), "A", null, 2002, false)
        )
        coEvery { localDataSource.getAllQuestions() } returns questions

        val result = repository.getLocalQuestions()

        assertEquals(1, result.size)
        assertEquals("Offline 1", result.first().question)
    }

    //4. Test fetchQuestionTypes -> luu Local
    @Test
    fun repository_fetchQuestionTypes_dataSavedToLocal() = runTest {

        val typeResponse = QuestionTypeResponse(
            types = listOf(
                QuestionType(1, "Loai A", 10)
            )
        )
        coEvery { remoteDataSource.getQuestionTypes() } returns ResponseResult.Success(typeResponse)

        val result = repository.fetchQuestionTypes()

        assertTrue(result is ResponseResult.Success)
        coVerify { localDataSource.insertQuestionTypes(match {it.size == 1}) }
    }
}