package com.example.driving_car_project.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.driving_car_project.data.model.Exam
import kotlinx.coroutines.flow.Flow

@Dao
interface ExamDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(exam: Exam): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(exams: List<Exam>)

    @Query("SELECT * FROM exams WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): Exam?

    @Query("SELECT * FROM exams ORDER BY createAt DESC")
    fun observeAll(): Flow<List<Exam>>

    @Delete
    suspend fun delete(exam: Exam)

    @Query("DELETE FROM exams")
    suspend fun clear()
}