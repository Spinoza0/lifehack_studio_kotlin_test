package com.spinoza.lifehackstudiotestkotlin

import com.google.gson.annotations.SerializedName

class CompanyInfoItem(
    @SerializedName("id")
    private var id: Int,
    @SerializedName("name")
    val name: String?,
    @SerializedName("img")
    private var img: String?,
) {

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
        get() = if (lat > 0.000001 && lon > 0.000001) "$lat / $lon" else ""

    fun getFullImgUrl() = "${ApiFactory.BASE_URL}$img"
}