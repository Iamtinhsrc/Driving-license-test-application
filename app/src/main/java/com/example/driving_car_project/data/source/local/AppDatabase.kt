package com.example.driving_car_project.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.driving_car_project.data.model.Answer
import com.example.driving_car_project.data.model.Exam
import com.example.driving_car_project.data.model.ExamHistory
import com.example.driving_car_project.data.model.Question
import com.example.driving_car_project.data.model.QuestionType
import com.example.driving_car_project.util.Converter

@Database(
    entities = [
        Answer::class,
        Exam::class,
        ExamHistory::class,
        Question::class,
        QuestionType::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converter::class)
abstract class AppDatabase :RoomDatabase() {

    abstract fun answerDao(): AnswerDao
    abstract fun examDao(): ExamDao
    abstract fun examHistoryDao(): ExamHistoryDao
    abstract fun questionDao(): QuestionDao
    abstract fun questionTypeDao(): QuestionTypeDao

//    companion object {
//        @Volatile
//        private var INSTANCE: AppDatabase? = null
//
//        fun getDatabase(context: Context): AppDatabase {
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    AppDatabase::class.java,
//                    "driving_car_db"
//                )
//                    .fallbackToDestructiveMigration()
//                    .build()
//                INSTANCE = instance
//                instance
//            }
//        }
//    }
}