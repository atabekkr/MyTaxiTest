package com.atabekdev.mytaxitest.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atabekdev.mytaxitest.ui.intent.LocationIntent
import com.atabekdev.mytaxitest.data.models.UserLocation
import com.atabekdev.mytaxitest.domain.usecase.LocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val useCase: LocationUseCase,
) : ViewModel() {

    private val _lastLocation = MutableStateFlow<UserLocation?>(null)
    val lastLocation: StateFlow<UserLocation?> = _lastLocation

    fun handleIntent(intent: LocationIntent) {
        viewModelScope.launch {
            when (intent) {
                is LocationIntent.GetLocations -> getLastLocation()
                // ... Other Intents goes here...
            }
        }
    }

    private suspend fun getLastLocation() {
        useCase.getLatestLocation().collect { location ->
            _lastLocation.value = location
        }
    }

}