package com.example.marvel.characters.viewholder

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.character_card.view.*

class CharacterViewHolder(
    private val view: View,
    private val onClick: (id: Int) -> Unit
) : RecyclerView.ViewHolder(view) {

    fun populate(imageUrl: String, id: Int, name: String) {
        with(view) {
            ivCharacter.contentDescription = name
            tvCharacter.text = name

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