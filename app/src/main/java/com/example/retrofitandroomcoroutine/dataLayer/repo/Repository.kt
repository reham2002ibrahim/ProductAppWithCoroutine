package com.example.retrofitandroomcoroutine.dataLayer.repo

import com.example.retrofitandroomcoroutine.dataLayer.local.LocalDataSource
import com.example.retrofitandroomcoroutine.dataLayer.model.Product
import com.example.retrofitandroomcoroutine.dataLayer.remote.RemoteDataSource
import kotlinx.coroutines.flow.Flow

class Repository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : RepositoryInterface {


    override suspend fun getAllProduct(isOnline: Boolean): List<Product>? {
        return if (isOnline) {
            remoteDataSource.getAllProducts()
        } else {
            localDataSource.getAllProducts()
        }
    }

    override suspend fun getFavProduct(): Flow<List<Product>> {
       return localDataSource.getFavProducts()
    }

    override suspend fun addProduct(product: Product): Long {
        return localDataSource.insert(product)
    }

    override suspend fun removeProduct(product: Product): Int {
        return localDataSource.delete(product)
    }

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            remoteDataSource: RemoteDataSource,
            localDataSource: LocalDataSource
        ): Repository {
            return instance ?: synchronized(this) {
                instance ?: Repository(remoteDataSource, localDataSource).also { instance = it }
            }
        }
    }


}