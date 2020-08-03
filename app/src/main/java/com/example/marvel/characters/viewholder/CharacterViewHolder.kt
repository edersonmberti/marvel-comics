package com.example.marvel.characters.viewholder

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marvel.model.Image
import kotlinx.android.synthetic.main.character_card.view.*

private const val IMAGE_VARIANT = "landscape_large"

class CharacterViewHolder(
    private val view: View,
    private val onClick: (id: Int) -> Unit
) : RecyclerView.ViewHolder(view) {

    fun populate(image: Image, id: Int, name: String) {
        with(view) {
            ivCharacter.contentDescription = name
            tvCharacter.text = name

            val imageUrl = image.run {
                "$path/$IMAGE_VARIANT.$extension"
            }

            Glide.with(context)
                .load(imageUrl)
                .placeholder(ColorDrawable(Color.GRAY))
                .error(ColorDrawable(Color.RED))
                .into(ivCharacter)

            setOnClickListener {
                onClick(id)
            }
        }
    }
}