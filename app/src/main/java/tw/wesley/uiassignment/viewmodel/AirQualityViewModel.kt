package tw.wesley.uiassignment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import tw.wesley.uiassignment.data.local.AirData
import tw.wesley.uiassignment.repo.AirDataRepository
import javax.inject.Inject

@HiltViewModel
class AirQualityViewModel @Inject constructor(
    private val airDataRepository: AirDataRepository
) : ViewModel() {

    private val _horizontalAirDataFlow = MutableStateFlow<List<AirData>>(emptyList())
    val horizontalAirDataFlow = _horizontalAirDataFlow.asStateFlow()

    private val _verticalAirDataFlow = MutableStateFlow<List<AirData>>(emptyList())
    val verticalAirDataFlow = _verticalAirDataFlow.asStateFlow()
    fun fetchAirData() {
        viewModelScope.launch {
            airDataRepository.fetchAndStoreAirQualityData()

            airDataRepository.getListAirDataFlow().collect { list ->
                list.sortedBy { it.pm25 }
                    .apply {
                        // Per requested, we need data in both horizontal and vertical recycler view
                        if (list.none { it.pm25 >= 30 }) {
                            // If the air is clean, no record has pm2.5 > 30
                            _horizontalAirDataFlow.emit(take(MIN_HORIZONTAL_ITEMS)) // take the first N
                            _verticalAirDataFlow.emit(drop(MIN_HORIZONTAL_ITEMS))   // ignore the first N
                        } else {
                            _horizontalAirDataFlow.emit(filter { it.pm25 <= 30 }) // takes PM2.5 <= 30
                            _verticalAirDataFlow.emit(filter { it.pm25 > 30 })    // takes PM2.5 > 30
                        }
                    }
            }
        }
    }

    companion object {
        const val MIN_HORIZONTAL_ITEMS = 5
    }
}