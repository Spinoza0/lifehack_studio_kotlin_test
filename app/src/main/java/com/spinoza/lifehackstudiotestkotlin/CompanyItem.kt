package com.spinoza.lifehackstudiotestkotlin

import com.google.gson.annotations.SerializedName

class CompanyItem {
    @SerializedName("id")
    val id: Int = 0

    @SerializedName("name")
    val name: String? = null

    @SerializedName("img")
    val img: String? = null
        get() = "${ApiFactory.BASE_URL}$field"
}
