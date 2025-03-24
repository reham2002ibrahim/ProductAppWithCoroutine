package com.example.retrofitandroomcoroutine.dataLayer.local

import com.example.retrofitandroomcoroutine.dataLayer.model.Product
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun getAllProducts(): List<Product>
    suspend fun getFavProducts(): Flow < List<Product> >
    suspend fun insertAll(products: List<Product>)
    suspend fun insert(product: Product): Long
    suspend fun update(product: Product)
    suspend fun delete(product: Product): Int
}
