package com.spinoza.lifehackstudiotestkotlin

import com.google.gson.annotations.SerializedName

class CompanyInfoItem {
    @SerializedName("id")
    val id: Int = 0

    @SerializedName("name")
    val name: String? = null

    @SerializedName("img")
    val img: String? = null
        get() = "${ApiFactory.BASE_URL}$field"

    @SerializedName("description")
    val description: String? = null

    @SerializedName("lat")
    private val lat = 0.0

    @SerializedName("lon")
    private val lon = 0.0

    @SerializedName("www")
    val www: String? = null

    @SerializedName("phone")
    val phone: String? = null

    val coordinates: String
        get() {
            var result = ""
            if (lat > 0.000001 && lon > 0.000001) result = "$lat / $lon"
            return result
        }
}