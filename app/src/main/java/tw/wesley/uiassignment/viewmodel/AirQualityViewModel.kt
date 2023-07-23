package tw.wesley.uiassignment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import tw.wesley.uiassignment.repo.AirDataRepository
import javax.inject.Inject

@HiltViewModel
class AirQualityViewModel @Inject constructor(
    private val airDataRepository: AirDataRepository
) : ViewModel() {

    fun fetchAirData() {
        viewModelScope.launch {
            airDataRepository.fetchAndStoreAirQualityData()
        }
    }
}