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
    private val locationService: LocationService
) : ViewModel() {
    val locationLiveData = MutableLiveData<LocationResponse?>()
    private var locationPage = 1

    suspend fun getLocations(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = locationService.getLocations(page)

            viewModelScope.launch(Dispatchers.Main) {
                locationLiveData.value = response
            }
        }
    }

    fun getLocationPage() = locationPage

}