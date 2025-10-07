package com.example.driving_car_project.di

import com.example.driving_car_project.ExamNavigator
import com.example.driving_car_project.HistoryNavigator
import com.example.driving_car_project.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton
import com.example.driving_car_project.di.RepositoryModule

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class]
)
object FakeRepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(): Repository = FakeRepository()

    @Provides
    @Singleton
    fun provideExamNavigator(): ExamNavigator = FakeExamNavigator()

    @Provides
    @Singleton
    fun provideHistoryNavigator(): HistoryNavigator = FakeHistoryNavigator()
}
