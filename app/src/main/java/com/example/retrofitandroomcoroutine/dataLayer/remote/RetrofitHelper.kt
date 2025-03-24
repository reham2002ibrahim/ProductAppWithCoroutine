package com.example.retrofitandroomcoroutine.dataLayer.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper{
    private const val BASE_URL="https://dummyjson.com/"
    val myRetrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService = myRetrofit.create(ProductApi::class.java)
}