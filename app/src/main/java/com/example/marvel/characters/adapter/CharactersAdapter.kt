package com.example.marvel.characters.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.marvel.R
import com.example.marvel.characters.viewholder.CharactersViewHolder
import com.example.marvel.model.CharacterPreview

class CharactersAdapter(
    private val characters: MutableList<CharacterPreview>,
    private val onClick: ((id: Int) -> Unit)
) : RecyclerView.Adapter<CharactersViewHolder>() {

    private var lastPosition = characters.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        val view =
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.character_card, parent, false)

        return CharactersViewHolder(view, onClick)
    }

    override fun getItemCount() = characters.size

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        characters[position].run {
            holder.populate(id = id, image = image, name = name)
        }
    }

    fun addItems(charactersToAdd: List<CharacterPreview>) {
        characters.addAll(charactersToAdd)
        notifyItemRangeInserted(lastPosition, charactersToAdd.size)
        lastPosition = characters.size
    }
}