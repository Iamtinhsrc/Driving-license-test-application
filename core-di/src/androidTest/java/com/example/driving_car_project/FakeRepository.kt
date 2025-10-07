package com.example.driving_car_project.di

import com.example.driving_car_project.Repository
import com.example.driving_car_project.model.*
import com.example.driving_car_project.remote.ResponseResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.util.*

class FakeRepository : Repository {

    override suspend fun fetchQuestions(): ResponseResult<QuestionResponse> =
        ResponseResult.Success(QuestionResponse(emptyList()))

    override suspend fun fetchQuestionTypes(): ResponseResult<QuestionTypeResponse> =
        ResponseResult.Success(QuestionTypeResponse(emptyList()))

    override suspend fun saveQuestions(questions: List<Question>) {}
    override suspend fun getLocalQuestions(): List<Question> = emptyList()
    override suspend fun getCriticalQuestions(): List<Question> = emptyList()
    override suspend fun getQuestionsByType(type: Int): List<Question> = emptyList()
    override suspend fun getQuestionById(id: Int): Question? = null
    override suspend fun saveAnswers(answers: List<Answer>) {}
    override suspend fun getAnswersByExamId(examId: Int): List<Answer> = emptyList()
    override suspend fun saveExam(exam: Exam): Long = 1
    override suspend fun getExamById(id: Int): Exam? = Exam(1, "Fake exam", listOf(1, 2), Date())
    override fun getAllExams(): Flow<List<Exam>> = flowOf(listOf(Exam(1, "Fake exam", listOf(1, 2), Date())))
    override suspend fun saveExamHistory(history: ExamHistory): Long = 1
    override suspend fun getExamHistoriesByExamId(examId: Int): List<ExamHistory> = emptyList()
    override fun getAllExamHistories(): Flow<List<HistoryWithExam>> = flowOf(emptyList())
    override suspend fun saveQuestionTypes(types: List<QuestionType>) {}
    override suspend fun getLocalQuestionTypes(): List<QuestionType> = emptyList()
}
