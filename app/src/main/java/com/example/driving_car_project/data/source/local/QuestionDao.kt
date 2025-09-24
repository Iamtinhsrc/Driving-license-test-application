package com.example.driving_car_project.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.driving_car_project.data.model.Question
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(question: Question)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(questions: List<Question>)

    @Query("SELECT * FROM questions WHERE id = :id LIMIT 1")
    suspend fun getQuestionById(id: Int): Question?

    @Query("SELECT * FROM questions")
    fun observerAll(): Flow<List<Question>>

    @Query("SELECT * FROM questions")
    suspend fun getAll(): List<Question>

    @Query("SELECT * FROM questions WHERE questionType = :type")
    suspend fun getByType(type: Int): List<Question>

    @Query("SELECT * FROM questions WHERE critical = 1")
    suspend fun getCriticals(): List<Question>

    @Query("DELETE FROM questions")
    suspend fun clear()
}