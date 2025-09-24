package com.example.driving_car_project.data.source.remote

sealed class ResponseResult<out T> {
    data class Success<T>(val data: T): ResponseResult<T>()
    data class Error(val message: String, val throwable: Throwable? = null): ResponseResult<Nothing>()
    data object Loading: ResponseResult<Nothing>()
}