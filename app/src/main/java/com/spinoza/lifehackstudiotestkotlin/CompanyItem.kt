package com.spinoza.lifehackstudiotestkotlin

import com.google.gson.annotations.SerializedName

class CompanyItem(
    @SerializedName("id")
    private var id: Int,
    @SerializedName("name")
    private var name: String?,
    @SerializedName("img")
    private var img: String?
) {

    fun getId(): Int = id
    fun getName(): String? = name
    fun getUrl(): String = "${ApiFactory.BASE_URL}$img"
    fun getImg(): String? = img

}
