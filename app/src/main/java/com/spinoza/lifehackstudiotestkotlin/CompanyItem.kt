package com.spinoza.lifehackstudiotestkotlin

import com.google.gson.annotations.SerializedName

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
