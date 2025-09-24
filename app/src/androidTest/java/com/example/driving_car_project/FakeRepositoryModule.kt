package com.example.driving_car_project

import com.example.driving_car_project.data.source.Repository
import com.example.driving_car_project.di.RepositoryModule
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class]   // thay thế module thật
)
object FakeRepositoryModule {

    @Provides
    @Singleton
    fun provideFakeRepository(): Repository = FakeRepository()
}
