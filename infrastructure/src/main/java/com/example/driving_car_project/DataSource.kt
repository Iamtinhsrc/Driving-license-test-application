package com.example.driving_car_project

import com.example.driving_car_project.model.Answer
import com.example.driving_car_project.model.Exam
import com.example.driving_car_project.model.ExamHistory
import com.example.driving_car_project.model.HistoryWithExam
import com.example.driving_car_project.model.Question
import com.example.driving_car_project.model.QuestionResponse
import com.example.driving_car_project.model.QuestionType
import com.example.driving_car_project.model.QuestionTypeResponse
import com.example.driving_car_project.remote.ResponseResult
import kotlinx.coroutines.flow.Flow

interface DataSource {
    interface LocalDataSource {
        // Question
        suspend fun insertQuestions(questions: List<Question>)
        suspend fun getAllQuestions(): List<Question>
        suspend fun getQuestionById(id: Int): Question?
        suspend fun getQuestionsByType(type: Int): List<Question>
        suspend fun getCriticalQuestions(): List<Question>
        suspend fun clearQuestions()

        // Answer
        suspend fun insertAnswers(answers: List<Answer>)
        suspend fun getAnswersByExamId(examId: Int): List<Answer>
        suspend fun getAnswerForQuestion(examId: Int, questionId: Int): Answer?
        suspend fun deleteAnswersByExamId(examId: Int)
        suspend fun clearAnswers()

        // Exam
        suspend fun insertExam(exam: Exam): Long
        suspend fun getExamById(id: Int): Exam?
        fun getAllExams(): Flow<List<Exam>>
        suspend fun deleteExam(exam: Exam)
        suspend fun clearExams()

        // ExamHistory
        suspend fun insertExamHistory(history: ExamHistory): Long
        suspend fun getExamHistoriesByExamId(examId: Int): List<ExamHistory>
         fun getAllExamHistories(): Flow<List<HistoryWithExam>>
        suspend fun deleteExamHistory(history: ExamHistory)
        suspend fun clearExamHistories()


        // QuestionType (Category)
        suspend fun insertQuestionTypes(types: List<QuestionType>)
        suspend fun getAllQuestionTypes(): List<QuestionType>
        suspend fun getQuestionTypeById(id: Int): QuestionType?
        suspend fun clearQuestionTypes()

    }

    interface RemoteDataSource {
        suspend fun getQuestions(): ResponseResult<QuestionResponse>
        suspend fun getQuestionTypes(): ResponseResult<QuestionTypeResponse>
    }
}