package com.example.driving_car_project.di

import android.content.Context
import androidx.room.Room
import com.example.driving_car_project.local.AnswerDao
import com.example.driving_car_project.local.AppDatabase
import com.example.driving_car_project.local.ExamDao
import com.example.driving_car_project.local.ExamHistoryDao
import com.example.driving_car_project.local.QuestionDao
import com.example.driving_car_project.local.QuestionTypeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "driving_car_db"
    ).fallbackToDestructiveMigration()
        .build()

    @Provides
    fun provideAnswerDao(db: AppDatabase): AnswerDao = db.answerDao()


    @Provides
    fun provideExamDao(db: AppDatabase): ExamDao = db.examDao()

    @Provides
    fun provideExamHistoryDao(db: AppDatabase): ExamHistoryDao = db.examHistoryDao()


    @Provides
    fun provideQuestionDao(db: AppDatabase): QuestionDao = db.questionDao()

    @Provides
    fun provideQuestionTypeDao(db: AppDatabase): QuestionTypeDao = db.questionTypeDao()


}