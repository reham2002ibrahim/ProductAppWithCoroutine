package com.example.retrofitandroomcoroutine.dataLayer.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import com.example.retrofitandroomcoroutine.dataLayer.model.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDAO {

    @Query("Select *from product")
    suspend fun gatAll() : List<Product>


    @Query("Select *from product")
    fun gatFav() : Flow<List<Product>>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(products: List<Product>)


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert (product: Product) :Long

    @Update
    suspend fun update (product: Product)

    @Delete
    suspend fun delete (product: Product) :Int



}