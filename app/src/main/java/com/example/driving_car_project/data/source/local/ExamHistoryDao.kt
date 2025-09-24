package com.example.driving_car_project.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.driving_car_project.data.model.ExamHistory
import com.example.driving_car_project.data.model.HistoryWithExam
import kotlinx.coroutines.flow.Flow

@Dao
interface ExamHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(history: ExamHistory): Long


    @Query("SELECT * FROM exam_history ORDER BY takenAt DESC")
    fun observerAll(): Flow<List<ExamHistory>>

    // --- Lấy danh sách history kèm exam ---
    @Transaction
    @Query("SELECT * FROM exam_history ORDER BY takenAt DESC")
    fun observeAllWithExam(): Flow<List<HistoryWithExam>>


    @Query("SELECT * FROM exam_history WHERE examId = :examId ORDER BY takenAt DESC")
    suspend fun getByExamId(examId: Int): List<ExamHistory>

    @Delete
    suspend fun delete(history: ExamHistory)

    @Query("DELETE FROM exam_history")
    suspend fun clear()
}