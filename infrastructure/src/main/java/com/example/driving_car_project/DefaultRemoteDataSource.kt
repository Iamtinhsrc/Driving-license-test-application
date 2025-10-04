package com.example.driving_car_project

import com.example.driving_car_project.remote.QuestionService
import com.example.driving_car_project.remote.ResponseResult
import com.example.driving_car_project.model.QuestionResponse
import com.example.driving_car_project.model.QuestionTypeResponse

class DefaultRemoteDataSource(
    private val service: QuestionService
) : DataSource.RemoteDataSource {

    override suspend fun getQuestions(): ResponseResult<QuestionResponse> {
        return try {
            val response = service.getQuestions()
            if (response.isSuccessful) {
                response.body()?.let { ResponseResult.Success(it) }
                    ?: ResponseResult.Error("Empty body")
            } else {
                ResponseResult.Error("Error code: ${response.code()}")
            }
        } catch (e: Exception) {
            ResponseResult.Error("Network error", e)
        }
    }

    override suspend fun getQuestionTypes(): ResponseResult<QuestionTypeResponse> {
        return try {
            val response = service.getQuestionTypes()
            if (response.isSuccessful) {
                response.body()?.let { ResponseResult.Success(it) }
                    ?: ResponseResult.Error("Empty body")
            } else {
                ResponseResult.Error("Error code: ${response.code()}")
            }
        } catch (e: Exception){
            ResponseResult.Error("Network error", e)
        }
    }


}