package com.example.driving_car_project.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.driving_car_project.model.QuestionType

@Dao
interface QuestionTypeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(types: List<QuestionType>)

    @Query("SELECT * FROM question_types")
    suspend fun getAll(): List<QuestionType>

    @Query("SELECT * FROM question_types WHERE id = :id")
    suspend fun getById(id: Int): QuestionType?

    @Query("DELETE FROM question_types")
    suspend fun clear()
}