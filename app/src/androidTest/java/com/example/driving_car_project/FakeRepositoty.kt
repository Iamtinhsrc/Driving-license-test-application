package com.example.driving_car_project

import com.example.driving_car_project.data.model.*
import com.example.driving_car_project.data.source.Repository
import com.example.driving_car_project.data.source.remote.ResponseResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeRepository @Inject constructor() : Repository {

    override suspend fun fetchQuestions(): ResponseResult<QuestionResponse> {
        val fakeQuestions = (1..100).map {
            Question(
                id = it,
                question = "Câu hỏi test $it?",
                option = Option("A", "B", "C", "D", null),
                image = Image(null, null, null, null),
                answer = "A",
                suggest = "Chọn A",
                questionType = 1,
                critical = false
            )
        }
        return ResponseResult.Success(QuestionResponse(fakeQuestions))
    }


    override suspend fun fetchQuestionTypes(): ResponseResult<QuestionTypeResponse> =
        ResponseResult.Success(QuestionTypeResponse(emptyList()))

    override suspend fun saveQuestions(questions: List<Question>) {}
    override suspend fun getLocalQuestions(): List<Question> {
        val list = (1..100).map {
            Question(
                id = it,
                question = "Câu hỏi test $it?",
                option = Option("A", "B", "C", "D", null),
                image = Image(null, null, null, null),
                answer = "A",
                suggest = "Chọn A",
                questionType = 1,
                critical = false
            )
        }
        return list
    }


    override suspend fun searchLocalQuestions(keyword: String): List<Question> = emptyList()
    override suspend fun getCriticalQuestions(): List<Question> = emptyList()
    override suspend fun getQuestionsByType(type: Int): List<Question> {
        return (1..10).map {
            Question(
                id = it,
                question = "Fake Exam Q$it?",
                option = Option("A", "B", "C", "D", null),
                image = Image(null, null, null, null),
                answer = "A",
                suggest = "Chọn A",
                questionType = type,
                critical = false
            )
        }
    }

    override suspend fun getQuestionById(id: Int): Question? = null
    override suspend fun saveAnswers(answers: List<Answer>) {}
    override suspend fun getAnswersByExamId(examId: Int): List<Answer> {
        return listOf(
            Answer(
                examId = examId,
                questionId = 1,
                selectedAnswer = "A",
                isCorrect = true
            )
        )
    }

    private val savedExams = mutableMapOf<Int, Exam>()

    override suspend fun saveExam(exam: Exam): Long {
        val newId = (savedExams.keys.maxOrNull() ?: 0) + 1
        val withId = exam.copy(id = newId)
        savedExams[newId] = withId
        return newId.toLong()
    }

    override suspend fun getExamById(id: Int): Exam? {
        return savedExams[id]
    }

    override fun getAllExams(): Flow<List<Exam>> = flowOf(emptyList())
    override suspend fun saveExamHistory(history: ExamHistory): Long = 0
    override suspend fun getExamHistoriesByExamId(examId: Int): List<ExamHistory> = emptyList()
    override fun getAllExamHistories(): Flow<List<HistoryWithExam>> = flowOf(
        listOf(
            HistoryWithExam(
                history = ExamHistory(
                    examId = 1,
                    score = 10,
                    total = 10,
                    passed = true,
                    takenAt = Date(),
                    durationSeconds = 300
                ),
                exam = Exam(id = 1, title = "Đề 10 câu", questionIds = listOf(1,2,3))
            )
        )
    )

    override suspend fun saveQuestionTypes(types: List<QuestionType>) {}
    override suspend fun getLocalQuestionTypes(): List<QuestionType> = emptyList()
}
