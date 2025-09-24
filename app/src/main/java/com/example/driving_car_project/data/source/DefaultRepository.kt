package com.example.driving_car_project.data.source

import android.util.Log
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

class DefaultRepository(
    private val localDataSource: DataSource.LocalDataSource,
    private val remoteDataSource: DataSource.RemoteDataSource
) : Repository {

    // Remote
    override suspend fun fetchQuestions(): ResponseResult<QuestionResponse> {
        return when (val result = remoteDataSource.getQuestions()) {
            is ResponseResult.Success -> {
                val questions = result.data.question
                localDataSource.clearQuestions()
                localDataSource.insertQuestions(questions)
                ResponseResult.Success(result.data)
            }
            is ResponseResult.Error -> result

            ResponseResult.Loading -> ResponseResult.Loading

        }

    }

    override suspend fun fetchQuestionTypes(): ResponseResult<QuestionTypeResponse> {
        return when (val result = remoteDataSource.getQuestionTypes()) {
            is ResponseResult.Success -> {
                val types = result.data.types.map {
                    QuestionType(it.id, it.title, it.quantity)
                }
                localDataSource.insertQuestionTypes(types)
                ResponseResult.Success(result.data)
            }
            is ResponseResult.Error -> result
            ResponseResult.Loading -> ResponseResult.Loading
        }
    }



    // Local
    override suspend fun saveQuestions(questions: List<Question>) {
        return localDataSource.insertQuestions(questions)
    }

    override suspend fun getLocalQuestions(): List<Question> {
        return localDataSource.getAllQuestions()
    }

    override suspend fun searchLocalQuestions(keyword: String): List<Question> {
        return localDataSource.searchQuestions(keyword)
    }

    override suspend fun getCriticalQuestions(): List<Question> {
        return localDataSource.getCriticalQuestions()
    }

    override suspend fun getQuestionsByType(type: Int): List<Question> {
        return localDataSource.getQuestionsByType(type)
    }

    override suspend fun getQuestionById(id: Int): Question? {
        return localDataSource.getQuestionById(id)
    }

    override suspend fun saveAnswers(answers: List<Answer>) {
        return localDataSource.insertAnswers(answers)
    }

    override suspend fun getAnswersByExamId(examId: Int): List<Answer> {
        return localDataSource.getAnswersByExamId(examId)
    }

    override suspend fun saveExam(exam: Exam): Long {
        return localDataSource.insertExam(exam)
    }

    override suspend fun getExamById(id: Int): Exam? {
        return localDataSource.getExamById(id)
    }

    override fun getAllExams(): Flow<List<Exam>> {
        return localDataSource.getAllExams()
    }

    override suspend fun saveExamHistory(history: ExamHistory): Long {
        return localDataSource.insertExamHistory(history)
    }

    override suspend fun getExamHistoriesByExamId(examId: Int): List<ExamHistory> {
        return localDataSource.getExamHistoriesByExamId(examId)
    }

    override fun getAllExamHistories(): Flow<List<HistoryWithExam>> {
        return localDataSource.getAllExamHistories()
    }


    override suspend fun saveQuestionTypes(types: List<QuestionType>) {
        return localDataSource.insertQuestionTypes(types)
    }

    override suspend fun getLocalQuestionTypes(): List<QuestionType> {
        return localDataSource.getAllQuestionTypes()
    }


}