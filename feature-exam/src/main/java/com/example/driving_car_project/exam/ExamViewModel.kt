package com.example.driving_car_project.exam

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.driving_car_project.Repository
import com.example.driving_car_project.model.Answer
import com.example.driving_car_project.model.Exam
import com.example.driving_car_project.model.ExamHistory
import com.example.driving_car_project.model.Question
import com.example.driving_car_project.di.DefaultDispatcher
import com.example.driving_car_project.di.IoDispatcher
import com.example.driving_car_project.di.MainDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.util.*
import javax.inject.Inject

data class ExamSessionResult(
    val examId: Int,
    val correct: Int,
    val total: Int,
    val passed: Boolean,
    val timeUsedSeconds: Int
)

@HiltViewModel
class ExamViewModel @Inject constructor(
    private val repository: Repository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _currentExam = MutableStateFlow<Exam?>(null)
    val currentExam: StateFlow<Exam?> = _currentExam.asStateFlow()

    private val _questions = MutableStateFlow<List<Question>>(emptyList())
    val questions: StateFlow<List<Question>> = _questions.asStateFlow()

    private val _currentIndex = MutableStateFlow(0)
    val currentIndex: StateFlow<Int> = _currentIndex.asStateFlow()

    private val _selectedAnswers = MutableStateFlow<Map<Int, String>>(emptyMap())
    val selectedAnswers: StateFlow<Map<Int, String>> = _selectedAnswers.asStateFlow()

    // Timer tổng đề
    private val _examTimeLeft = MutableStateFlow(0)
    val examTimeLeft: StateFlow<Int> = _examTimeLeft.asStateFlow()

    // Timer từng câu (map theo questionId)
    private val _questionTimeLeft = MutableStateFlow<Map<Int, Int>>(emptyMap())
    val questionTimeLeft: StateFlow<Map<Int, Int>> = _questionTimeLeft.asStateFlow()

    private val _isRunning = MutableStateFlow(false)
    val isRunning: StateFlow<Boolean> = _isRunning.asStateFlow()

    private val _resultEvents = MutableSharedFlow<ExamSessionResult>(replay = 1)
    val resultEvents: SharedFlow<ExamSessionResult> = _resultEvents.asSharedFlow()

    private val _showSubmitConfirm = MutableSharedFlow<Unit>()
    val showSubmitConfirm: SharedFlow<Unit> = _showSubmitConfirm

    private var timerJob: Job? = null
    private var passThresholdPercent = 80.0
    private val perQuestionSeconds = 30

    fun createAndStartExam(numQuestions: Int, title: String? = null) {
        viewModelScope.launch(ioDispatcher) {
            var pool = repository.getLocalQuestions()
            if (pool.isEmpty()) {
                repository.fetchQuestions()
                pool = repository.getLocalQuestions()
            }
            val chosen = pool.shuffled().take(numQuestions)

            val examTitle = title ?: "Đề $numQuestions câu"
            val exam = Exam(title = examTitle, questionIds = chosen.map { it.id })
            val saveId = repository.saveExam(exam)
            val savedExam = exam.copy(id = saveId.toInt())

            _currentExam.value = savedExam
            _questions.value = chosen
            _selectedAnswers.value = emptyMap()
            _currentIndex.value = 0
            _isRunning.value = true

            val totalSeconds = when (numQuestions) {
                10 -> 300
                20 -> 600
                30 -> 900
                else -> numQuestions * 30
            }
            _examTimeLeft.value = totalSeconds

            // Mỗi câu 30s)
            _questionTimeLeft.value = chosen.associate { it.id to perQuestionSeconds }

            startTimers()
        }
    }

    fun loadExam(examId: Int, isPreview: Boolean = false) {
        viewModelScope.launch(ioDispatcher) {
            val exam = repository.getExamById(examId) ?: return@launch
            val allLocal = repository.getLocalQuestions()
            val ordered = exam.questionIds.mapNotNull { id -> allLocal.find { it.id == id } }

            _currentExam.value = exam
            _questions.value = ordered
            _currentIndex.value = 0
            _isRunning.value = !isPreview

            if (isPreview) {
                // Load lại đáp án đã lưu để highlight đúng/sai
                val savedAnswers = repository.getAnswersByExamId(examId)
                _selectedAnswers.value = savedAnswers.associate { it.questionId to (it.selectedAnswer ?: "") }
            } else {
                _selectedAnswers.value = emptyMap()
                val totalSeconds = when (ordered.size) {
                    10 -> 300
                    20 -> 600
                    30 -> 900
                    else -> ordered.size * 30
                }
                _examTimeLeft.value = totalSeconds
                _questionTimeLeft.value = ordered.associate { it.id to perQuestionSeconds }
                startTimers()
            }
        }
    }

    private fun startTimers() {
        cancelTimer()
        timerJob = viewModelScope.launch(ioDispatcher) {
            while (_isRunning.value && _examTimeLeft.value > 0) {
                delay(1000)
                // Giảm exam timer
                _examTimeLeft.value -= 1

                val q = _questions.value.getOrNull(_currentIndex.value)
                if (q != null) {
                    val map = _questionTimeLeft.value.toMutableMap()
                    val left = map[q.id] ?: perQuestionSeconds
                    if (left > 0) {
                        map[q.id] = left - 1
                        _questionTimeLeft.value = map
                        if (left - 1 == 0) {
                            // Hết giờ câu → coi như sai, qua câu sau
                            autoFailQuestion(q.id)
                            next()
                        }
                    }
                }

                if (_examTimeLeft.value <= 0) {
                    submitExam()
                }
            }
        }
    }

    private fun cancelTimer() {
        timerJob?.cancel()
        timerJob = null
    }

    private suspend fun autoFailQuestion(qId: Int) {
        val examId = _currentExam.value?.id ?: return
        if (!_selectedAnswers.value.containsKey(qId)) {
            val ans = Answer(
                examId = examId,
                questionId = qId,
                selectedAnswer = null,
                isCorrect = false
            )
            repository.saveAnswers(listOf(ans))
        }
    }

    fun chooseAnswer(questionId: Int, label: String) {
        _selectedAnswers.value = _selectedAnswers.value.toMutableMap().apply {
            this[questionId] = label
        }

        viewModelScope.launch(ioDispatcher) {
            val examId = _currentExam.value?.id ?: return@launch
            val q = _questions.value.find { it.id == questionId } ?: return@launch
            val isCorrect = q.answer?.equals(label, true) ?: false
            val answer = Answer(
                examId = examId,
                questionId = questionId,
                selectedAnswer = label,
                isCorrect = isCorrect
            )
            repository.saveAnswers(listOf(answer))
        }
    }

    fun next() {
        if (_currentIndex.value < _questions.value.size - 1) {
            _currentIndex.value += 1
        } else {
            if (_isRunning.value) { // chỉ khi đang làm bài thì mới hiện dialog nộp
                viewModelScope.launch {
                    _showSubmitConfirm.emit(Unit)
                }
            }
        }
    }


    fun prev() {
        if (_currentIndex.value > 0) {
            _currentIndex.value -= 1
        }
    }

    fun submitExam() {
        viewModelScope.launch(defaultDispatcher) {
            cancelTimer()
            _isRunning.value = false

            val exam = _currentExam.value ?: return@launch
            val qs = _questions.value
            val persisted = repository.getAnswersByExamId(exam.id).associateBy { it.questionId }

            var correctCount = 0
            val answersToSave = qs.map { q ->
                val selected = _selectedAnswers.value[q.id] ?: persisted[q.id]?.selectedAnswer
                val isCorrect = selected?.equals(q.answer, true) ?: false
                if (isCorrect) correctCount++
                Answer(exam.id, q.id, selected, isCorrect)
            }

            repository.saveAnswers(answersToSave)

            val total = qs.size
            val passed = evaluatePass(correctCount, total)

            val totalSeconds = when (qs.size) {
                10 -> 300
                20 -> 600
                30 -> 900
                else -> qs.size * 30
            }
            val usedSeconds = totalSeconds - _examTimeLeft.value
            val history = ExamHistory(
                examId = exam.id,
                score = correctCount,
                total = total,
                passed = passed,
                takenAt = Date(),
                durationSeconds = usedSeconds
            )
            repository.saveExamHistory(history)

            val result = ExamSessionResult(
                examId = exam.id,
                correct = correctCount,
                total = total,
                passed = passed,
                timeUsedSeconds = usedSeconds
            )

            _resultEvents.emit(result)
        }
    }

    private fun evaluatePass(correct: Int, total: Int): Boolean {
        val percent = if (total == 0) 0.0 else correct.toDouble() / total * 100.0
        return percent >= passThresholdPercent
    }

    override fun onCleared() {
        super.onCleared()
        cancelTimer()
    }
}
