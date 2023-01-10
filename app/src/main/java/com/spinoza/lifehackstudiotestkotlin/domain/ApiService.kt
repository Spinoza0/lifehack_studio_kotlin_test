package com.spinoza.lifehackstudiotestkotlin.domain

import io.reactivex.rxjava3.core.Single

interface ApiService {
    fun loadCompanies(): Single<List<CompanyItem>>
    fun loadCompanyInfo(id: Int): Single<List<CompanyInfoItem>>
}