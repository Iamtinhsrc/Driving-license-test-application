package com.example.driving_car_project.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.driving_car_project.util.Converter

@Entity(tableName = "questions")
data class Question(
    @PrimaryKey val id: Int,
    val question: String,
    @TypeConverters(Converter::class) val option: Option,
    @TypeConverters(Converter::class) val image: Image,
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