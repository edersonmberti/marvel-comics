package com.example.marvel.services

import com.example.marvel.model.CharacterDataWrapper
import com.example.marvel.model.CharactersDataWrapper
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val TS = "thesoer"
const val HASH = "8ecb9d1ae1393b6fd4178945a3fcc1ee"
const val API_KEY = "013f3cbc470985603d11f1b0c58a816b"

interface CharactersService {
    @GET("characters")
    fun getAll(
        @Query("offset") offset: Int,
        @Query("ts") ts: String? = TS ,
        @Query("hash") hash: String? = HASH,
        @Query("apikey") apikey: String? = API_KEY
    ): Call<CharactersDataWrapper>

    @GET("characters/{id}")
    fun getCharacterInfo(
        @Path("id") id: Int,
        @Query("ts") ts: String? = TS,
        @Query("hash") hash: String? = HASH,
        @Query("apikey") apikey: String? = API_KEY
    ): Call<CharacterDataWrapper>
}