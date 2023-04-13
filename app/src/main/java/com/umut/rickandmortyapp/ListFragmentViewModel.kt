package com.umut.rickandmortyapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListFragmentViewModel @Inject constructor(
    private val locationService: LocationService,
    private val characterService: CharacterService
) : ViewModel() {
    val locationLiveData = MutableLiveData<LocationResponse?>()
    val charactersLiveData = MutableLiveData<MutableList<Character?>?>()
    private var firstTimeLoading = true
    private var locations: MutableList<Location?>? = mutableListOf()
    private var locationPage = 1

    suspend fun getLocations(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = locationService.getLocations(page)

            viewModelScope.launch(Dispatchers.Main) {
                locationLiveData.value = response
            }
        }
    }

    suspend fun getCharacters(characters: List<Int>) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = characterService.getCharacters(characters)

            viewModelScope.launch(Dispatchers.Main) {
                charactersLiveData.value = response
            }
        }
    }

    fun getLocationPage() = locationPage

    fun checkIfFirstTimeLoading() = firstTimeLoading

    fun loadedForFirstTime() {
        firstTimeLoading = false
    }

    fun incrementLocationPage() = locationPage++

    fun setMovieList(locations: MutableList<Location?>?){
        locations?.let { this.locations?.addAll(it) }
    }

    fun getMovieList(): MutableList<Location?>?{
        return this.locations
    }

}