package com.example.driving_car_project.di.navigation

import com.example.driving_car_project.ExamNavigator
import com.example.driving_car_project.navigation.impl.ExamNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ExamNavigatorModule {
    @Binds
    abstract fun bindExamNavigator(
        impl: ExamNavigatorImpl
    ): ExamNavigator
}