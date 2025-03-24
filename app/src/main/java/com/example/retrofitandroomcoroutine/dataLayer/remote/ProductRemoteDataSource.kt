package com.example.retrofitandroomcoroutine.dataLayer.remote

import com.example.retrofitandroomcoroutine.dataLayer.model.Product

class ProductRemoteDataSource(private val service: ProductApi) : RemoteDataSource {
    override suspend fun getAllProducts(): List<Product>? {
        return service.getProduct().body()?.products

    }
}
