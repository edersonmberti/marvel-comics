package com.example.marvel.model

import com.google.gson.annotations.SerializedName

data class CharactersDataContainer(
    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    @SerializedName("results")
    val characters: List<CharacterPreview>
)