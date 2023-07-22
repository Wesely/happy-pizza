package tw.wesley.uiassignment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import tw.wesley.uiassignment.network.AirQualityApiService
import javax.inject.Inject

@HiltViewModel
class AirQualityViewModel @Inject constructor(
    private val airQualityApiService: AirQualityApiService
) : ViewModel() {


    // TODO: This is just a POC, remove it later in task #3
    fun printAirQualityInLog() {
        viewModelScope.launch {
            Timber.d(airQualityApiService.getAirQualityData().string())
        }
    }
}