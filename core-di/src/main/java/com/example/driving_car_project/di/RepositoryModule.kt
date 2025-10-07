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
    fun provideLocalDataSource(db: AppDatabase): DataSource.LocalDataSource = DefaultLocalDataSource(db)

    @Provides
    @Singleton
    fun provideRemoteDataSource(service: QuestionService): DataSource.RemoteDataSource = DefaultRemoteDataSource(service)

    // Repository
    @Provides
    @Singleton
    fun provideRepository(
        localDataSource: DataSource.LocalDataSource,
        remoteDataSource: DataSource.RemoteDataSource
    ) : Repository =
        DefaultRepository(localDataSource, remoteDataSource)
}