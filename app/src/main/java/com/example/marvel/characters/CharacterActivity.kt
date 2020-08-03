package com.example.marvel.characters

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.marvel.R

const val CHARACTER_ID = "characterId"

class CharacterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character)
    }
}