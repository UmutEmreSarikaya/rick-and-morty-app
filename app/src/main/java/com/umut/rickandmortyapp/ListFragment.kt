package com.umut.rickandmortyapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.umut.rickandmortyapp.databinding.FragmentListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListFragment : Fragment() {
    private val viewModel: ListFragmentViewModel by viewModels()
    private lateinit var binding: FragmentListBinding
    private val locationListAdapter = LocationListAdapter(::onItemClickListener)
    private val characterListAdapter = CharacterListAdapter()
    private val characterIDList = mutableListOf<Int>()
    private var characterID: Int? = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(layoutInflater)

        setupRecyclerViews()

        lifecycleScope.launch {
            viewModel.getLocations(viewModel.getLocationPage())
        }

        viewModel.locationLiveData.observe(viewLifecycleOwner){ locations ->
            locationListAdapter.setLocations(locations?.results)

            for (item in viewModel.locationLiveData.value?.results?.get(0)?.residentsURL ?: mutableListOf()){
                characterID = item?.split("/")?.last()?.toInt()
                characterID?.let { characterIDList.add(it) }
            }

            lifecycleScope.launch {
                viewModel.getCharacters(characterIDList)
            }

            viewModel.charactersLiveData.observe(viewLifecycleOwner){
                characterListAdapter.setCharacters(it)
            }
        }

        return binding.root
    }

    private fun onItemClickListener(position: Int) {
        characterIDList.clear()

        for (item in viewModel.locationLiveData.value?.results?.get(position)?.residentsURL ?: mutableListOf()){
            characterID = item?.split("/")?.last()?.toInt()
            characterID?.let { characterIDList.add(it) }
        }

        lifecycleScope.launch {
            viewModel.getCharacters(characterIDList)
        }

        viewModel.charactersLiveData.observe(viewLifecycleOwner){
            characterListAdapter.setCharacters(it)
        }
    }

    private fun setupRecyclerViews(){
        binding.locationRecyclerView.adapter = locationListAdapter
        binding.characterRecyclerView.adapter = characterListAdapter

        val locationLayoutManager = LinearLayoutManager(activity)
        locationLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.locationRecyclerView.layoutManager = locationLayoutManager

        val characterLayoutManager = LinearLayoutManager(activity)
        binding.characterRecyclerView.layoutManager = characterLayoutManager
    }

}