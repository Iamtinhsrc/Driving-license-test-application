//package com.example.driving_car_project
//
//import com.example.driving_car_project.Repository
//import com.example.driving_car_project.di.RepositoryModule
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.components.SingletonComponent
//import dagger.hilt.testing.TestInstallIn
//import javax.inject.Singleton
//
//@Module
//@TestInstallIn(
//    components = [SingletonComponent::class],
//    replaces = [RepositoryModule::class]
//)
//object FakeRepositoryModule {
//
//    @Provides
//    @Singleton
//    fun provideFakeRepository(): com.example.driving_car_project.Repository = FakeRepository()
//}
