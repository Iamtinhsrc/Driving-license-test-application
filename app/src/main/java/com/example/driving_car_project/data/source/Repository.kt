package com.example.driving_car_project.data.source

import com.example.driving_car_project.data.model.Answer
import com.example.driving_car_project.data.model.Exam
import com.example.driving_car_project.data.model.ExamHistory
import com.example.driving_car_project.data.model.HistoryWithExam
import com.example.driving_car_project.data.model.Question
import com.example.driving_car_project.data.model.QuestionResponse
import com.example.driving_car_project.data.model.QuestionType
import com.example.driving_car_project.data.model.QuestionTypeResponse
import com.example.driving_car_project.data.source.remote.ResponseResult
import kotlinx.coroutines.flow.Flow

interface Repository {
    // Remote
    suspend fun fetchQuestions(): ResponseResult<QuestionResponse>
    suspend fun fetchQuestionTypes(): ResponseResult<QuestionTypeResponse>

    // Local
    suspend fun saveQuestions(questions: List<Question>)
    suspend fun getLocalQuestions(): List<Question>
    suspend fun searchLocalQuestions(keyword: String): List<Question>
    suspend fun getCriticalQuestions(): List<Question>
    suspend fun getQuestionsByType(type: Int): List<Question>
    suspend fun getQuestionById(id: Int): Question?

    suspend fun saveAnswers(answers: List<Answer>)
    suspend fun getAnswersByExamId(examId: Int): List<Answer>

    suspend fun saveExam(exam: Exam): Long
    suspend fun getExamById(id: Int): Exam?
    fun getAllExams(): Flow<List<Exam>>

    suspend fun saveExamHistory(history: ExamHistory): Long
    suspend fun getExamHistoriesByExamId(examId: Int): List<ExamHistory>
    fun getAllExamHistories(): Flow<List<HistoryWithExam>>


    suspend fun saveQuestionTypes(types: List<QuestionType>)
    suspend fun getLocalQuestionTypes(): List<QuestionType>

}