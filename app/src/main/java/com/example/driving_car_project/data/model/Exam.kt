package com.example.driving_car_project.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.util.Date

@Entity(tableName = "exams")
data class Exam(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,           // Loai de 10-20-30 cau
    val questionIds: List<Int>,  // Danh sach cau hoi Random
    val createAt: Date = Date()  // Ngay tao de
)
