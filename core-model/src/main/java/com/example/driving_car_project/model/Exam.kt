package com.example.driving_car_project.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.util.Date

@Entity(tableName = "exams")
data class Exam(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val questionIds: List<Int>,
    val createAt: Date = Date()
)
