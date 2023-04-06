package com.umut.rickandmortyapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.umut.rickandmortyapp.databinding.ItemCharacterBinding

class CharacterListAdapter: RecyclerView.Adapter<CharacterListAdapter.CharacterListHolder>() {
    private var characters : MutableList<Character?>? = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterListHolder {
        val itemBinding =
            ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterListHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CharacterListHolder, position: Int) {
        holder.bindItems(characters?.get(position))
    }

    override fun getItemCount() = characters?.size ?: 0

    inner class CharacterListHolder(private val itemBinding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bindItems(character: Character?) {
            itemBinding.textCharacter.text = character?.name
            Glide.with(itemView).load(character?.imageURL).into(itemBinding.imageCharacter)

        }
    }

    fun setCharacters(characters: MutableList<Character?>?) {
        this.characters = characters
        notifyDataSetChanged()
    }

}
