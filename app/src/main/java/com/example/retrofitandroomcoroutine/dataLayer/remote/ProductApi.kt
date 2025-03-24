package com.example.retrofitandroomcoroutine.dataLayer.remote

import com.example.retrofitandroomcoroutine.dataLayer.model.ProductResponse
import retrofit2.Response
import retrofit2.http.GET

interface ProductApi {
    @GET("product")
    suspend fun getProduct(): Response<ProductResponse>
}
