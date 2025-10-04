package com.example.driving_car_project.di

import com.example.driving_car_project.DataSource
import com.example.driving_car_project.DefaultRepository
import com.example.driving_car_project.Repository
import com.example.driving_car_project.local.AppDatabase
import com.example.driving_car_project.DefaultLocalDataSource
import com.example.driving_car_project.remote.QuestionService
import com.example.driving_car_project.DefaultRemoteDataSource
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
    fun provideLocalDataSource(db: AppDatabase): com.example.driving_car_project.DataSource.LocalDataSource =
        com.example.driving_car_project.DefaultLocalDataSource(db)

    @Provides
    @Singleton
    fun provideRemoteDataSource(service: QuestionService): com.example.driving_car_project.DataSource.RemoteDataSource =
        com.example.driving_car_project.DefaultRemoteDataSource(service)

    // Repository
    @Provides
    @Singleton
    fun provideRepository(
        localDataSource: com.example.driving_car_project.DataSource.LocalDataSource,
        remoteDataSource: com.example.driving_car_project.DataSource.RemoteDataSource
    ) : com.example.driving_car_project.Repository =
        com.example.driving_car_project.DefaultRepository(localDataSource, remoteDataSource)
}