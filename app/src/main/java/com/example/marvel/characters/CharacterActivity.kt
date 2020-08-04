package com.example.marvel.characters

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.marvel.R
import com.example.marvel.extensions.showToast
import com.example.marvel.favorites.FavoritesSharedPreferencesService
import com.example.marvel.model.CharacterDataWrapper
import com.example.marvel.model.CharacterPreview
import com.example.marvel.services.RetrofitClient
import kotlinx.android.synthetic.main.activity_character.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

const val CHARACTER_ID = "characterId"
const val LANDSCAPE_INCREDIBLE = "landscape_incredible"

class CharacterActivity : AppCompatActivity() {

    private var id: Int = 0
    private lateinit var characterViewModel: CharacterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character)

        intent?.run {
            id = getIntExtra(CHARACTER_ID, 0)
        }

        characterViewModel = ViewModelProvider(
            this,
            CharacterViewModelFactory(RetrofitClient.getCharacterService())
        ).get(CharacterViewModel::class.java)

        initiateObservers()
        checkFavorite()
        characterViewModel.loadCharacter(id)

        fabFavoriteCharacter.setOnClickListener { toggleFavorite() }
    }

    private fun initiateObservers() {
        characterViewModel.run {
            observeToShowToast(showMessageLoading, R.string.loading)
            observeToShowToast(showMessageError, R.string.something_went_wrong)

            isFavorite.observe(this@CharacterActivity, Observer { isFavorite ->
                toggleFavoriteIcon(isFavorite)
            })

            character.observe(this@CharacterActivity, Observer { character ->
                setCharacterUI(character)
            })
        }
    }

    private fun observeToShowToast(liveData: LiveData<Boolean>, messageId: Int) {
        liveData.observe(this@CharacterActivity, Observer { shouldShow ->
            if (shouldShow) showToast(messageId)
        })
    }

    private fun setCharacterUI(characterPreview: CharacterPreview) {
        characterPreview.run {
            val imageUrl = image.run {
                "$path/$LANDSCAPE_INCREDIBLE.$extension"
            }

            Glide.with(this@CharacterActivity)
                .load(imageUrl)
                .placeholder(ColorDrawable(Color.GRAY))
                .error(ColorDrawable(Color.RED))
                .into(ivCharacter)

            tvDescription.text = description
        }
    }

    private fun checkFavorite() {
        val isFavorite = FavoritesSharedPreferencesService.isFavorite(this, id)

        characterViewModel.setIsFavorite(isFavorite)
    }

    private fun toggleFavorite() {
        characterViewModel.run {
            val favoriteValue = if (isFavorite.value !!) {
                FavoritesSharedPreferencesService.remove(this@CharacterActivity, id).not()
            } else {
                FavoritesSharedPreferencesService.add(this@CharacterActivity, id)
            }

            setIsFavorite(favoriteValue)
        }
    }

    private fun toggleFavoriteIcon(isFavorite: Boolean) {
        fabFavoriteCharacter.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                if (isFavorite) R.drawable.ic_favorite_filled else R.drawable.ic_favorite
            )
        )
    }

}