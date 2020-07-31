package com.example.marvel.characters.adpter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.marvel.R
import com.example.marvel.characters.viewholder.CharacterViewHolder
import com.example.marvel.model.CharacterPreview

class CharactersAdapter(
    private val characters: MutableList<CharacterPreview>,
    private val onClick: ((id: Int) -> Unit)
) : RecyclerView.Adapter<CharacterViewHolder>() {

    private var lastPosition = characters.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view =
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.character_card, parent, false)

        return CharacterViewHolder(view, onClick)
    }

    override fun getItemCount() = characters.size

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        characters[position].run {
            holder.populate(id = id, imageUrl = image, name = name)
        }
    }

    fun addItems(charactersToAdd: List<CharacterPreview>) {
        characters.addAll(charactersToAdd)
        notifyItemRangeInserted(lastPosition, charactersToAdd.size)
        lastPosition = characters.size
    }
}