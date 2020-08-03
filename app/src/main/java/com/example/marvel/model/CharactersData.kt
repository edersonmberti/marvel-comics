package com.example.marvel.model

import com.google.gson.annotations.SerializedName

data class CharactersData(
    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    @SerializedName("results")
    val characters: List<CharacterPreview>
)