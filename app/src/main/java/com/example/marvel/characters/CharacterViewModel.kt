package com.example.marvel.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marvel.model.CharacterDataWrapper
import com.example.marvel.model.CharacterPreview
import com.example.marvel.services.CharactersService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharacterViewModel(private val charactersService: CharactersService): ViewModel() {

    private val _showMessageLoading = MutableLiveData<Boolean>()
    val showMessageLoading: LiveData<Boolean>
        get() = _showMessageLoading

    private val _showMessageError = MutableLiveData<Boolean>()
    val showMessageError: LiveData<Boolean>
        get() = _showMessageError

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean>
        get() = _isFavorite

    private val _character = MutableLiveData<CharacterPreview>()
    val character: LiveData<CharacterPreview>
        get() = _character

    fun setIsFavorite(value: Boolean) = _isFavorite.postValue(value)

    fun loadCharacter(id: Int) {
        _showMessageLoading.postValue(true)

        charactersService
            .getCharacterInfo(id)
            .enqueue(object : Callback<CharacterDataWrapper> {
            override fun onResponse(
                call: Call<CharacterDataWrapper>?,
                response: Response<CharacterDataWrapper>?
            ) {
                response?.takeIf { it.isSuccessful }?.run {
                    body()?.data?.run {
                        _showMessageLoading.postValue(false)
                        _character.postValue(characters[0])
                    }
                }
            }

            override fun onFailure(call: Call<CharacterDataWrapper>?, t: Throwable?) {
                _showMessageLoading.postValue(false)
                _showMessageError.postValue(true)
            }
        })
    }

}