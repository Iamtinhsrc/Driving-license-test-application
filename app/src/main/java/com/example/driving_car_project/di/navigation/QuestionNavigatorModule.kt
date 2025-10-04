package com.example.driving_car_project.di.navigation

import com.example.driving_car_project.QuestionNavigator
import com.example.driving_car_project.navigation.impl.QuestionNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class QuestionNavigatorModule {
    @Binds
    abstract fun bindQuestionNavigator(
        impl: QuestionNavigatorImpl
    ): QuestionNavigator
}