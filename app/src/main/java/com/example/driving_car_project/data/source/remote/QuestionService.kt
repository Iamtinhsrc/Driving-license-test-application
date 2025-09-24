package com.example.driving_car_project.data.source.remote

import com.example.driving_car_project.data.model.QuestionResponse
import com.example.driving_car_project.data.model.QuestionTypeResponse
import retrofit2.Response
import retrofit2.http.GET

interface QuestionService {
    @GET("driver_test.json")
    suspend fun getQuestions(): Response<QuestionResponse>

    @GET("test_type.json")
    suspend fun getQuestionTypes(): Response<QuestionTypeResponse>
}