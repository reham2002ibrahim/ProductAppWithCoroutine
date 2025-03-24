package com.example.retrofitandroomcoroutine.dataLayer.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.retrofitandroomcoroutine.dataLayer.model.Product


@Database(entities = arrayOf(Product::class), version = 1 , exportSchema = false)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun getProductDao(): ProductDAO

    companion object {
        @Volatile


        private var instanceOfDB: ProductDatabase? = null
        fun getInstance(context: Context): ProductDatabase {

            return instanceOfDB ?: synchronized(this) {
                val temp: ProductDatabase =
                    Room.databaseBuilder(context, ProductDatabase::class.java, "productdb").build()
                instanceOfDB = temp
                temp

            }
            //  return Room.databaseBuilder(context, ProductDatabase::class.java, "productdb").build()

        }

    }


}