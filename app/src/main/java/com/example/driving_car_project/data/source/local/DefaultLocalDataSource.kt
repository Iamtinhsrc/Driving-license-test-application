package com.example.driving_car_project.data.source.local

import com.example.driving_car_project.data.model.Answer
import com.example.driving_car_project.data.model.Exam
import com.example.driving_car_project.data.model.ExamHistory
import com.example.driving_car_project.data.model.HistoryWithExam
import com.example.driving_car_project.data.model.Question
import com.example.driving_car_project.data.model.QuestionType
import com.example.driving_car_project.data.source.DataSource
import kotlinx.coroutines.flow.Flow

class DefaultLocalDataSource(
    private val db: AppDatabase
) : DataSource.LocalDataSource {

    private val questionDao = db.questionDao()
    private val answerDao = db.answerDao()
    private val examDao = db.examDao()
    private val examHistoryDao = db.examHistoryDao()
    //private val guideDao = db.guideDao()
    private val questionTypeDao = db.questionTypeDao()

    // Question
    override suspend fun insertQuestions(questions: List<Question>) {
        return questionDao.insertAll(questions)
    }

    override suspend fun getAllQuestions(): List<Question> {
        return questionDao.getAll()
    }

    override suspend fun getQuestionById(id: Int): Question? {
        return questionDao.getQuestionById(id)
    }

    override suspend fun getQuestionsByType(type: Int): List<Question> {
        return questionDao.getByType(type)
    }

    override suspend fun getCriticalQuestions(): List<Question> {
        return questionDao.getCriticals()
    }

    override suspend fun searchQuestions(keyword: String): List<Question> {
        return questionDao.searchByKeyword(keyword)
    }

    override suspend fun clearQuestions() {
        return questionDao.clear()
    }

    // Answer

    override suspend fun insertAnswers(answers: List<Answer>) {
        return answerDao.insertAll(answers)
    }

    override suspend fun getAnswersByExamId(examId: Int): List<Answer> {
        return answerDao.getByExamId(examId)
    }

    override suspend fun getAnswerForQuestion(examId: Int, questionId: Int): Answer? {
        return answerDao.getForQuestion(examId, questionId)
    }

    override suspend fun deleteAnswersByExamId(examId: Int) {
        return answerDao.deleteByExamId(examId)
    }

    override suspend fun clearAnswers() {
        return answerDao.clear()
    }

    // Exam

    override suspend fun insertExam(exam: Exam): Long {
        return examDao.insert(exam)
    }

    override suspend fun getExamById(id: Int): Exam? {
        return examDao.getById(id)
    }

    override  fun getAllExams(): Flow<List<Exam>> {
        return examDao.observeAll()
    }

    override suspend fun deleteExam(exam: Exam) {
        return examDao.delete(exam)
    }

    override suspend fun clearExams() {
        return examDao.clear()
    }

    // Exam History

    override suspend fun insertExamHistory(history: ExamHistory): Long {
        return examHistoryDao.insert(history)
    }

    override suspend fun getExamHistoriesByExamId(examId: Int): List<ExamHistory> {
        return examHistoryDao.getByExamId(examId)
    }

    override fun getAllExamHistories(): Flow<List<HistoryWithExam>> {
        return examHistoryDao.observeAllWithExam()
    }

    override suspend fun deleteExamHistory(history: ExamHistory) {
        return examHistoryDao.delete(history)
    }

    override suspend fun clearExamHistories() {
        return examHistoryDao.clear()
    }


    // Category (QuestionType)
    override suspend fun insertQuestionTypes(types: List<QuestionType>) {
        return questionTypeDao.insertAll(types)
    }

    override suspend fun getAllQuestionTypes(): List<QuestionType> {
        return questionTypeDao.getAll()
    }

    override suspend fun getQuestionTypeById(id: Int): QuestionType? {
        return questionTypeDao.getById(id)
    }

    override suspend fun clearQuestionTypes() {
        return questionTypeDao.clear()
    }

}