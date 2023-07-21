package tw.wesley.uiassignment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import timber.log.Timber
import tw.wesley.uiassignment.network.AirQualityApiService

class AirQualityViewModel : ViewModel() {

    // TODO: This is just a POC, remove it later in task #3
    fun printAirQualityInLog() {
        val apiService = Retrofit.Builder()
            .baseUrl("https://data.epa.gov.tw")
            .build()
            .create(AirQualityApiService::class.java)

        viewModelScope.launch {
            Timber.d(apiService.getAirQualityData().string())
        }
    }
}