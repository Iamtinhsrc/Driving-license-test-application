package com.example.driving_car_project.di.navigation

import com.example.driving_car_project.CategoryNavigator
import com.example.driving_car_project.navigation.impl.CategoryNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CategoryNavigatorModule {
    @Binds
    abstract fun bindCategoryNavigator(
        impl: CategoryNavigatorImpl
    ): CategoryNavigator

}