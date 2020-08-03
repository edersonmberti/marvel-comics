package com.example.marvel.model

import com.google.gson.annotations.SerializedName

data class CharacterPreview(
    val id: Int,
    val name: String,
    val description: String,
    @SerializedName("thumbnail")
    val image: Image
)