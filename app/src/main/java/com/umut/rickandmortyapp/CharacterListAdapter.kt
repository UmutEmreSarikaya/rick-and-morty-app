package com.umut.rickandmortyapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.umut.rickandmortyapp.databinding.ItemCharacterEvenBinding
import com.umut.rickandmortyapp.databinding.ItemCharacterOddBinding

class CharacterListAdapter(val goToDetailPage: (Character?) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var characters: MutableList<Character?>? = mutableListOf()

    companion object {
        private const val VIEW_TYPE_EVEN = 0
        private const val VIEW_TYPE_ODD = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_EVEN -> {
                val itemBinding =
                    ItemCharacterEvenBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                EvenCharacterListHolder(itemBinding)
            }
            else -> {
                val itemBinding =
                    ItemCharacterOddBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                OddCharacterListHolder(itemBinding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is EvenCharacterListHolder -> holder.bindItems(characters?.get(position))
            is OddCharacterListHolder -> holder.bindItems(characters?.get(position))
        }
    }

    override fun getItemCount() = characters?.size ?: 0

    override fun getItemViewType(position: Int): Int {
        return if (position % 2 == 0) {
            VIEW_TYPE_EVEN
        } else {
            VIEW_TYPE_ODD
        }
    }

    inner class EvenCharacterListHolder(private val itemBinding: ItemCharacterEvenBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        init {
            itemBinding.characterCardView.setOnClickListener {
                goToDetailPage(characters?.get(adapterPosition))
            }
        }

        fun bindItems(character: Character?) {
            itemBinding.textCharacter.text = character?.name
            //val requestOptions = RequestOptions()
            //requestOptions.placeholder(R.drawable.loading_buffering)
            //requestOptions.error(R.drawable.baseline_question_mark_24)
            Glide.with(itemView).load(character?.imageURL).into(itemBinding.imageCharacter)
            when (character?.gender) {
                "Male" -> {
                    Glide.with(itemView).load(R.drawable.baseline_male_24)
                        .into(itemBinding.imageGender)
                }
                "Female" -> {
                    Glide.with(itemView).load(R.drawable.baseline_female_24)
                        .into(itemBinding.imageGender)
                }
                "unknown" -> {
                    Glide.with(itemView).load(R.drawable.baseline_question_mark_24)
                        .into(itemBinding.imageGender)
                }
            }
        }
    }

    inner class OddCharacterListHolder(private val itemBinding: ItemCharacterOddBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        init {
            itemBinding.characterCardView.setOnClickListener {
                goToDetailPage(characters?.get(adapterPosition))
            }
        }

        fun bindItems(character: Character?) {
            itemBinding.textCharacter.text = character?.name
            //val requestOptions = RequestOptions()
            //requestOptions.placeholder(R.drawable.loading_buffering)
            //requestOptions.error(R.drawable.baseline_question_mark_24)
            Glide.with(itemView).load(character?.imageURL).into(itemBinding.imageCharacter)
            when (character?.gender) {
                "Male" -> {
                    Glide.with(itemView).load(R.drawable.baseline_male_24)
                        .into(itemBinding.imageGender)
                }
                "Female" -> {
                    Glide.with(itemView).load(R.drawable.baseline_female_24)
                        .into(itemBinding.imageGender)
                }
                "unknown" -> {
                    Glide.with(itemView).load(R.drawable.baseline_question_mark_24)
                        .into(itemBinding.imageGender)
                }
            }
        }
    }

    fun setCharacters(characters: MutableList<Character?>?) {
        this.characters = characters
        notifyDataSetChanged()
    }
}
