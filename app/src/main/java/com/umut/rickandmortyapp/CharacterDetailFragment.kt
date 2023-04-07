package com.umut.rickandmortyapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.umut.rickandmortyapp.databinding.FragmentCharacterDetailBinding

class CharacterDetailFragment : Fragment() {
    private lateinit var binding: FragmentCharacterDetailBinding
    private var character: Character? = null
    private val args: CharacterDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterDetailBinding.inflate(layoutInflater)

        character = args.character

        binding.headerText.text = character?.name
        Glide.with(this).load(character?.imageURL).into(binding.imageCharacter)
        binding.characterStatusText.text = character?.status
        binding.characterSpeciesText.text = character?.species
        binding.characterGenderText.text = character?.gender
        binding.characterOriginText.text = character?.origin?.originName
        binding.characterLocationText.text = character?.location?.characterLocationName
        binding.characterEpisodesText.text = character?.gender
        binding.characterCreatedDateText.text = character?.created

        binding.backButton.setOnClickListener {
            val action = CharacterDetailFragmentDirections.actionCharacterDetailFragmentToListFragment()
            findNavController().navigate(action)
        }

        return binding.root
    }
}