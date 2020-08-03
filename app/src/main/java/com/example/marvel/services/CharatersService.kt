package com.example.marvel.services

import com.example.marvel.model.CharacterDataWrapper
import com.example.marvel.model.CharactersDataWrapper
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharactersService {
    @GET("characters")
    fun getAll(
        @Query("ts") ts: String,
        @Query("hash") hash: String,
        @Query("apikey") apikey: String,
        @Query("offset") offset: Int
    ): Call<CharactersDataWrapper>

    @GET("characters/{id}")
    fun getCharacterInfo(
        @Path("id") id: Int,
        @Query("ts") ts: String,
        @Query("hash") hash: String,
        @Query("apikey") apikey: String
    ): Call<CharacterDataWrapper>
}