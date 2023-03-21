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
    private val locationListAdapter = LocationListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(layoutInflater)

        binding.locationRecyclerView.adapter = locationListAdapter

        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.locationRecyclerView.layoutManager = linearLayoutManager



        lifecycleScope.launch {
            viewModel.getLocations(viewModel.getLocationPage())
        }

        viewModel.locationLiveData.observe(viewLifecycleOwner){
            locationListAdapter.setLocations(it?.results)
        }

        return binding.root
    }

}