package com.example.marvel.model

import com.google.gson.annotations.SerializedName

data class CharacterDataWrapper (
    @SerializedName("data")
    val response: CharactersResponse
)