package com.example.driving_car_project.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "questions")
data class Question(
    @PrimaryKey val id: Int,
    val question: String,
    val option: Option,
    val image: Image,
    val answer: String?,
    val suggest: String?,
    val questionType: Int,
    val critical: Boolean
)

data class Option(
    val a: String?,
    val b: String?,
    val c: String?,
    val d: String?,
    val e: String?
)

data class Image(
    val img1: String?,
    val img2: String?,
    val img3: String?,
    val img4: String?
)

//Wrapper cho API question
data class QuestionResponse(
    val question: List<Question>
)