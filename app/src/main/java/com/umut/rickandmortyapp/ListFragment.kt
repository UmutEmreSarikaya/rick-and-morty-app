package com.umut.rickandmortyapp

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.umut.rickandmortyapp.databinding.FragmentListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListFragment : Fragment() {
    private val viewModel: ListFragmentViewModel by viewModels()
    private lateinit var binding: FragmentListBinding
    private val locationListAdapter = LocationListAdapter(::onLocationClick)
    private val characterListAdapter = CharacterListAdapter(::goToDetailPage)
    private lateinit var locationLayoutManager: LinearLayoutManager
    private var isFragmentVisible = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(layoutInflater)

        setupRecyclerViews()

        if (viewModel.checkIfFirstTimeLoading()) {
            if (isInternetConnected(requireContext())) {
                lifecycleScope.launch {
                    viewModel.getLocations(viewModel.getCurrentPage())
                }
            } else {
                Toast.makeText(requireContext(), "NO INTERNET CONNECTION!", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        viewModel.locationLiveData.observe(viewLifecycleOwner) {
            if (isFragmentVisible) {
                viewModel.setLocationList(it?.results)
                locationListAdapter.setLocations(viewModel.getLocationList())
            }

            if (viewModel.checkIfFirstTimeLoading()) {
                viewModel.setTotalPageNumber(it?.information?.pages)
                onLocationClick(0)
                viewModel.loadedForFirstTime()
            }
        }

        binding.locationRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                if (!recyclerView.canScrollHorizontally(1) && viewModel.getCurrentPage() < viewModel.getTotalPageNumber()) {
                    binding.progressBar.visibility = View.VISIBLE
                    viewModel.incrementCurrentPage()
                    if (isInternetConnected(requireContext())) {
                        lifecycleScope.launch {
                            viewModel.getLocations(viewModel.getCurrentPage())
                            binding.progressBar.visibility = View.GONE
                        }
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "NO INTERNET CONNECTION!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }
            }
        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        isFragmentVisible = true
    }

    override fun onPause() {
        super.onPause()
        isFragmentVisible = false
    }

    private fun onLocationClick(position: Int) {
        var characterID: Int?
        val characterIDList = mutableListOf<Int>()
        characterIDList.clear()

        for (item in viewModel.getLocationList()?.get(position)?.residentsURL
            ?: mutableListOf()) {
            characterID = item?.split("/")?.last()?.toInt()
            characterID?.let { characterIDList.add(it) }
        }

        lifecycleScope.launch {
            if (characterIDList.isEmpty()) {
                binding.infoText.visibility = View.VISIBLE
                binding.characterRecyclerView.visibility = View.GONE
            } else {
                binding.infoText.visibility = View.GONE
                binding.characterRecyclerView.visibility = View.VISIBLE
                if (isInternetConnected(requireContext())) {
                    lifecycleScope.launch {
                        viewModel.getCharacters(characterIDList)
                    }
                } else {
                    Toast.makeText(requireContext(), "NO INTERNET CONNECTION!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        viewModel.charactersLiveData.observe(viewLifecycleOwner) {
            characterListAdapter.setCharacters(it)
        }
    }

    private fun goToDetailPage(character: Character?) {
        val action = ListFragmentDirections.actionListFragmentToCharacterDetailFragment(character)
        findNavController().navigate(action)
    }

    private fun setupRecyclerViews() {
        binding.locationRecyclerView.adapter = locationListAdapter
        binding.characterRecyclerView.adapter = characterListAdapter

        locationLayoutManager = LinearLayoutManager(activity)
        locationLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.locationRecyclerView.layoutManager = locationLayoutManager

        val characterLayoutManager = LinearLayoutManager(activity)
        binding.characterRecyclerView.layoutManager = characterLayoutManager
    }

    private fun isInternetConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val activeNetwork =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}