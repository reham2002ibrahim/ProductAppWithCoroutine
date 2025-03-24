package com.example.retrofitandroomcoroutine.dataLayer.remote

import com.example.retrofitandroomcoroutine.dataLayer.model.Product

interface RemoteDataSource {
    suspend fun getAllProducts(): List<Product>?
}
