package com.example.driving_car_project.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.util.Date

@Entity(tableName = "exam_history")
data class ExamHistory(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val examId: Int,              // Lien ket Exam
    val score: Int,               // So cau dung
    val total: Int,               // Tong so cau
    val passed: Boolean,          // Dau hay truot
    val takenAt: Date = Date(),   // Thoi gian lam bai
    val durationSeconds: Int = 0  // Khoang thoi gian lam bai
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

// 1-n
data class ExamWithHistories(
    @Embedded val exam: Exam,
    @Relation(
        parentColumn = "id",
        entityColumn = "examId"
    )
    val histories: List<ExamHistory>
)

