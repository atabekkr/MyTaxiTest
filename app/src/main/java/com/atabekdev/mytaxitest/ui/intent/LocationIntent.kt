package com.atabekdev.mytaxitest.ui.intent

sealed class LocationIntent{
    data object GetLocations : LocationIntent()
}