package com.example.driving_car_project.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "answers", primaryKeys = ["examId","questionId"])
data class Answer(
    val examId: Int,
    val questionId: Int,
    val selectedAnswer: String?,
    val isCorrect: Boolean
)


data class AnswerOption(
    val label: String,          // A / B / C / D / E
    val text: String,           // Nội dung đáp án
    val isCorrect: Boolean,    // Sau khi kiểm tra mới set
    val suggest: String? = null
)
