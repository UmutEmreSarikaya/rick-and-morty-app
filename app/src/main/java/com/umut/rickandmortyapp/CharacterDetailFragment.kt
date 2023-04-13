package com.umut.rickandmortyapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.umut.rickandmortyapp.databinding.FragmentCharacterDetailBinding
import java.text.SimpleDateFormat
import java.util.*

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


        Glide.with(this).load(character?.imageURL).into(binding.imageCharacter)
        binding.apply {
            headerText.text = character?.name
            characterStatusText.text = character?.status
            characterSpeciesText.text = character?.species
            characterGenderText.text = character?.gender
            characterOriginText.text = character?.origin?.originName
            characterLocationText.text = character?.location?.characterLocationName
            characterEpisodesText.text = extractEpisodes(character?.episodes)
            characterCreatedDateText.text = formatDate(character?.created)

            backButton.setOnClickListener {
                /*val action = CharacterDetailFragmentDirections.actionCharacterDetailFragmentToListFragment()
                findNavController().navigate(action)*/
                findNavController().popBackStack()
            }
        }

        return binding.root
    }

    private fun formatDate(inputDate: String?): String? {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val date = inputDate?.let { inputFormat.parse(it) }

        val outputFormat = SimpleDateFormat("d MMM yyyy, HH:mm:ss", Locale("en", "US"))
        return date?.let { outputFormat.format(it) }
    }

    private fun extractEpisodes(episodeURLList: List<String?>?): String{
        var episodeID: String?
        val episodeIDList = mutableListOf<String>()
        for (item in episodeURLList ?: listOf()){
            episodeID = item?.split("/")?.last()
            episodeID?.let { episodeIDList.add(it) }
        }
        return episodeIDList.joinToString(separator = ", ", prefix = "", postfix = "")
    }
}