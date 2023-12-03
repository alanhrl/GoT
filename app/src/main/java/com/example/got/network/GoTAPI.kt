package com.example.got.network

import com.example.got.model.Personaje
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface GoTAPI {
    // https://thronesapi.com/api/v2/Characters/24
    @GET
    fun getCharacters(
        @Url url: String?
    ): Call<ArrayList<Personaje>>

    @GET("api/v2/Characters/{id}")
    fun getCharacterDetail(
        @Path("id") id: String? //        @Query("id") id: String
    ):Call<Personaje>


}