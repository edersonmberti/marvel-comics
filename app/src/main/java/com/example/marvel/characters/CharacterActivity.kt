package com.example.marvel.characters

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.marvel.R
import com.example.marvel.favorites.FavoritesSharedPreferencesService
import com.example.marvel.model.CharacterDataWrapper
import com.example.marvel.services.RetrofitClient
import kotlinx.android.synthetic.main.activity_character.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

const val CHARACTER_ID = "characterId"
const val LANDSCAPE_INCREDIBLE = "landscape_incredible"

class CharacterActivity : AppCompatActivity() {

    private var id: Int = 0
    private var isFavoriteCharacter = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character)
        intent?.run {
            id = getIntExtra(CHARACTER_ID, 0)
        }
        loadCharacter(this)
        fabFavoriteCharacter.setOnClickListener { toggleFavorite() }
    }

    private fun loadCharacter(context: Context) {
        isFavoriteCharacter = FavoritesSharedPreferencesService.isFavorite(this, id)
        toggleFavoriteIcon()

        RetrofitClient
            .getCharacterService()
            .getCharacterInfo(id, TS, HASH, API_KEY)
            .enqueue(object : Callback<CharacterDataWrapper> {
                override fun onResponse(
                    call: Call<CharacterDataWrapper>?,
                    response: Response<CharacterDataWrapper>?
                ) {
                    response?.takeIf { it.isSuccessful }?.run {
                        body()?.data?.run {
                            characters[0].run {
                                val imageUrl = image.run {
                                    "$path/$LANDSCAPE_INCREDIBLE.$extension"
                                }

                                Glide.with(context)
                                    .load(imageUrl)
                                    .placeholder(ColorDrawable(Color.GRAY))
                                    .error(ColorDrawable(Color.RED))
                                    .into(ivCharacter)

                                tvDescription.text = description
                            }
                        }
                    } ?: Log.d("response", response.toString())
                }

                override fun onFailure(call: Call<CharacterDataWrapper>?, t: Throwable?) {
                    Log.d("Throwable", t.toString())
                }
            })
    }

    private fun toggleFavorite() {
        isFavoriteCharacter = if (isFavoriteCharacter) {
            FavoritesSharedPreferencesService.remove(this, id).not()
        } else {
            FavoritesSharedPreferencesService.add(this, id)
        }
        toggleFavoriteIcon()
    }

    private fun toggleFavoriteIcon() {
        fabFavoriteCharacter.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                if (isFavoriteCharacter) R.drawable.ic_favorite_filled else R.drawable.ic_favorite
            )
        )
    }

}