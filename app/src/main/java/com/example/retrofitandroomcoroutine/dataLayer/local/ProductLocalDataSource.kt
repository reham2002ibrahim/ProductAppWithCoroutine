package com.example.retrofitandroomcoroutine.dataLayer.local

import com.example.retrofitandroomcoroutine.dataLayer.model.Product
import kotlinx.coroutines.flow.Flow

class ProductLocalDataSource(private val dao: ProductDAO) : LocalDataSource {

    override suspend fun getAllProducts(): List<Product> = dao.gatAll()
    override suspend fun getFavProducts(): Flow<List<Product>> = dao.gatFav()
    override suspend fun insertAll(products: List<Product>) {
        dao.insertAll(products)
    }

    override suspend fun insert(product: Product): Long = dao.insert(product)

    override suspend fun update(product: Product) {
        dao.update(product)
    }

    override suspend fun delete(product: Product): Int = dao.delete(product)
}
