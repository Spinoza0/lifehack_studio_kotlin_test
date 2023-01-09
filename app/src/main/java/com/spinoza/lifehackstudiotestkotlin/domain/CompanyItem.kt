package com.spinoza.lifehackstudiotestkotlin.domain

import com.google.gson.annotations.SerializedName
import com.spinoza.lifehackstudiotestkotlin.data.ApiFactory

class CompanyItem(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String?,
    @SerializedName("img")
    val img: String?,
) {
    fun getFullImgUrl() = "${ApiFactory.BASE_URL}$img"
}
