package com.example.got.model

import com.google.gson.annotations.SerializedName


data class Personaje(
    @SerializedName("id")
    var id: String?,

    @SerializedName("firstName")
    var firstName: String?,

    @SerializedName("lastName")
    var lastName: String?,

    @SerializedName("fullName")
    var fullName:String?,

    @SerializedName("title")
    var title:String?,

    @SerializedName("family")
    var family: String?,

    @SerializedName("image")
    var image: String?,

    @SerializedName("imageUrl")
    var imageUrl: String?
)
