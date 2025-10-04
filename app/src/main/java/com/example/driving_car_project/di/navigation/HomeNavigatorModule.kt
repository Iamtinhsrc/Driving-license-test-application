package com.example.driving_car_project.di.navigation

import com.example.driving_car_project.HomeNavigator
import com.example.driving_car_project.navigation.impl.HomeNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class HomeNavigatorModule {
    @Binds
    abstract fun bindHomeNavigator(
        impl: HomeNavigatorImpl
    ): HomeNavigator
}