package com.spinoza.lifehackstudiotestkotlin

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("test.php")
    fun loadCompanies(): Single<List<CompanyItem>>

    @GET("test.php")
    fun loadCompanyInfo(@Query("id") id: Int): Single<List<CompanyInfoItem>>
}