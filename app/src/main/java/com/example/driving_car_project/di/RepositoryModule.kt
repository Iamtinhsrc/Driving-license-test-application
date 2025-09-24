package com.example.driving_car_project.di

import com.example.driving_car_project.data.source.DataSource
import com.example.driving_car_project.data.source.DefaultRepository
import com.example.driving_car_project.data.source.Repository
import com.example.driving_car_project.data.source.local.AnswerDao
import com.example.driving_car_project.data.source.local.AppDatabase
import com.example.driving_car_project.data.source.local.ExamDao
import com.example.driving_car_project.data.source.local.ExamHistoryDao
import com.example.driving_car_project.data.source.local.DefaultLocalDataSource
import com.example.driving_car_project.data.source.local.QuestionDao
import com.example.driving_car_project.data.source.remote.QuestionService
import com.example.driving_car_project.data.source.remote.DefaultRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    // DataSources
    @Provides
    @Singleton
    fun provideLocalDataSource(db: AppDatabase): DataSource.LocalDataSource =
        DefaultLocalDataSource(db)

    @Provides
    @Singleton
    fun provideRemoteDataSource(service: QuestionService): DataSource.RemoteDataSource =
        DefaultRemoteDataSource(service)

    // Repository
    @Provides
    @Singleton
    fun provideRepository(
        localDataSource: DataSource.LocalDataSource,
        remoteDataSource: DataSource.RemoteDataSource
    ) : Repository =
        DefaultRepository(localDataSource, remoteDataSource)
}