package com.example.marvel.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marvel.extensions.showToast
import com.example.marvel.model.CharacterPreview
import com.example.marvel.model.CharactersDataWrapper
import com.example.marvel.services.CharactersService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharactersViewModel(private val charactersService: CharactersService): ViewModel() {

    private var currentPage = 0

    private val _hasReachedEnd = MutableLiveData<Boolean>()
    val showMessageHasReachedEnd: LiveData<Boolean>
        get() = _hasReachedEnd

    private val _showMessageLoading = MutableLiveData<Boolean>()
    val showMessageLoading: LiveData<Boolean>
        get() = _showMessageLoading

    private val _showMessageError = MutableLiveData<Boolean>()
    val showMessageError: LiveData<Boolean>
        get() = _showMessageError

    private val _characters = MutableLiveData<List<CharacterPreview>>()
    val characters: LiveData<List<CharacterPreview>>
        get() = _characters

    fun loadCharacters() {
        if (_hasReachedEnd.value == true) return

        _showMessageLoading.postValue(true)

        charactersService.getAll(currentPage).enqueue(object : Callback<CharactersDataWrapper> {
            override fun onResponse(
                call: Call<CharactersDataWrapper>?,
                response: Response<CharactersDataWrapper>?
            ) {
                response?.takeIf { it.isSuccessful }?.run {
                    body()?.data?.run {
                        currentPage++
                        _hasReachedEnd.postValue((total / limit) > offset + 1)
                        _characters.postValue(characters)
                    }
                }
            }

            override fun onFailure(call: Call<CharactersDataWrapper>?, t: Throwable?) {
                _showMessageLoading.postValue(false)
                _showMessageError.postValue(true)
            }
        })
    }
}