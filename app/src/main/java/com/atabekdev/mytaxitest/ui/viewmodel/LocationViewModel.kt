package com.atabekdev.mytaxitest.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atabekdev.mytaxitest.data.models.UserLocation
import com.atabekdev.mytaxitest.domain.usecase.AddLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val useCase: AddLocationUseCase
): ViewModel() {

    init {
        getLastLocation()
    }

    private val _lastLocation = MutableStateFlow<UserLocation?>(null)
    val lastLocation: StateFlow<UserLocation?> = _lastLocation

    private fun getLastLocation() {
        viewModelScope.launch {
            useCase.getAllLocations().collect { location ->
                _lastLocation.value = location
            }
        }
    }

}