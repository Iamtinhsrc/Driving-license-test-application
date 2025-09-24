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
    val label: String,
    val text: String,
    val isCorrect: Boolean,
    val suggest: String? = null
)
