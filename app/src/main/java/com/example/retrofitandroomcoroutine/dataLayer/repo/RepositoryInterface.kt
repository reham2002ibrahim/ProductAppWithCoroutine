package com.example.retrofitandroomcoroutine.dataLayer.repo

import com.example.retrofitandroomcoroutine.dataLayer.model.Product
import kotlinx.coroutines.flow.Flow

interface RepositoryInterface {
    suspend fun getAllProduct(isOnline: Boolean): List<Product>?
    suspend fun getFavProduct(): Flow < List<Product>>
    suspend fun addProduct(product: Product): Long
    suspend fun removeProduct(product: Product): Int
}
