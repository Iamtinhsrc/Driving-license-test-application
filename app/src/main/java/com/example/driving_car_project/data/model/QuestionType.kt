package com.example.driving_car_project.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "question_types")
data class QuestionType(
    @PrimaryKey val id: Int,
    val title: String,
    val quantity: Int
)

//Wrapper cho API type
data class QuestionTypeResponse(
    val types: List<QuestionType>
)

// 1-n
data class QuestionTypeWithQuestions(
    @Embedded val type: QuestionType,
    @Relation(
        parentColumn = "id",
        entityColumn = "questionType"
    )
    val questions: List<Question>
)
