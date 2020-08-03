package com.example.marvel.services

import com.example.marvel.model.CharacterPreview
import com.example.marvel.model.CharactersResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharactersService {
    @GET("characters")
    fun getAll(@Query("ts") ts: String,
               @Query("apikey") apikey: String,
               @Query("hash") hash: String,
               @Query("offset") offset: Int): Call<CharactersResponse>

    @GET("characters/{id}?ts=thesoer&apikey=013f3cbc470985603d11f1b0c58a816b&hash=8ecb9d1ae1393b6fd4178945a3fcc1ee")
    fun getCharacterInfo(@Path("id") id: Int): Call<CharacterPreview>
}