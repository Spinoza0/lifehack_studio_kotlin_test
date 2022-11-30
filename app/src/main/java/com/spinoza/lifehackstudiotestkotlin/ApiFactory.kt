package com.spinoza.lifehackstudiotestkotlin

import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {
    const val BASE_URL = "https://lifehack.studio/test_task/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()

    @JvmField
    val apiService: ApiService = retrofit.create(ApiService::class.java)
}