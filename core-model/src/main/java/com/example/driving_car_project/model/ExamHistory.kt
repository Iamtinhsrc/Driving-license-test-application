package com.example.driving_car_project.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.util.Date

@Entity(tableName = "exam_history")
data class ExamHistory(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val examId: Int,
    val score: Int,
    val total: Int,
    val passed: Boolean,
    val takenAt: Date = Date(),
    val durationSeconds: Int = 0
)

// 1-1
data class HistoryWithExam(
    @Embedded val history: ExamHistory,
    @Relation(
        parentColumn = "examId",
        entityColumn = "id"
    )
    val exam: Exam?
)

