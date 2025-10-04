package com.example.driving_car_project.di.navigation

import com.example.driving_car_project.GuideNavigator
import com.example.driving_car_project.navigation.impl.GuideNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class GuideNavigatorModule {
    @Binds
    abstract fun bindGuideNavigator(
        impl: GuideNavigatorImpl
    ): GuideNavigator
}