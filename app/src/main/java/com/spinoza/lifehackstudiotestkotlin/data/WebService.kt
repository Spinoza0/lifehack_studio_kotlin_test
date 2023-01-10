package com.spinoza.lifehackstudiotestkotlin.data

import com.spinoza.lifehackstudiotestkotlin.domain.ApiService
import com.spinoza.lifehackstudiotestkotlin.domain.CompanyInfoItem
import com.spinoza.lifehackstudiotestkotlin.domain.CompanyItem
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService : ApiService {
    @GET("test.php")
    override fun loadCompanies(): Single<List<CompanyItem>>

    @GET("test.php")
    override fun loadCompanyInfo(@Query("id") id: Int): Single<List<CompanyInfoItem>>
}