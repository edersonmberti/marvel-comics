package com.example.marvel.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.marvel.services.CharactersService

class CharacterViewModelFactory(private val charactersService: CharactersService) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(CharactersService::class.java)
            .newInstance(charactersService)
    }
}