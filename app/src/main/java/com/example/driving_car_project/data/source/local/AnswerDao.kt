package com.example.driving_car_project.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.driving_car_project.data.model.Answer
import kotlinx.coroutines.flow.Flow

@Dao
interface AnswerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(answer: Answer): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(answers: List<Answer>)


    @Query("SELECT * FROM answers WHERE examId = :examId")
    fun observerByExamId(examId: Int): Flow<List<Answer>>

    @Query("SELECT * FROM answers WHERE examId = :examId")
    suspend fun getByExamId(examId: Int): List<Answer>

    @Query("SELECT * FROM answers WHERE examId = :examId AND questionId = :questionId LIMIT 1")
    suspend fun getForQuestion(examId: Int, questionId: Int): Answer?

   @Query("DELETE FROM answers WHERE examId = :examId")
   suspend fun deleteByExamId(examId: Int)

   @Query("DELETE FROM answers")
   suspend fun clear()
}