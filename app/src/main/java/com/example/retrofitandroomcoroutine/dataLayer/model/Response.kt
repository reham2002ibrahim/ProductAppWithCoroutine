package com.example.retrofitandroomcoroutine.dataLayer.model

sealed class Response {
    object Loading : Response()
    data class Success(val data: List<Product>) : Response()
    data class Failure(val error: Throwable) : Response()
}