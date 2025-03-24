package com.example.retrofitandroomcoroutine.dataLayer.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "product")
data class Product(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    var title: String?,
    var price: String?,
    var brand: String?,
    var description: String?,
    var rating: String?,
    var thumbnail: String?
): Serializable
