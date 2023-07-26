package tw.wesley.uiassignment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import tw.wesley.uiassignment.data.local.AirData
import tw.wesley.uiassignment.repo.AirDataRepository
import javax.inject.Inject

@HiltViewModel
class AirQualityViewModel @Inject constructor(
    // Dependency injection to get the repository
    private val airDataRepository: AirDataRepository
) : ViewModel() {

    // MutableLiveData is a LiveData whose value can be changed.
    // _horizontalAirLiveData is private so the UI cannot change its value.
    private val _horizontalAirLiveData = MutableLiveData<List<AirData>>(emptyList())
    val horizontalAirLiveData: LiveData<List<AirData>> = _horizontalAirLiveData

    private val _verticalAirLiveData = MutableLiveData<List<AirData>>(emptyList())
    val verticalAirLiveData: LiveData<List<AirData>> = _verticalAirLiveData

    // This function is called when we want to refresh the air data
    fun fetchAirData() {
        // It runs in a coroutine to avoid blocking the main thread
        viewModelScope.launch {
            airDataRepository.fetchAndStoreAirQualityData()
        }
    }

    init {
        // Here we start collecting the Flow from our repository when viewModel is live
        viewModelScope.launch {
            // collectLatest would discard and we start processing the new one.
            airDataRepository.getListAirDataFlow().collectLatest { list ->
                Timber.d("getListAirDataFlow/dataSize=${list.size}}")
                // Sort and split the list based on PM2.5 value
                list.sortedBy { it.pm25 }.apply {
                    // Determine which air data goes to which LiveData based on PM2.5 value
                    if (list.none { it.pm25 >= 30 }) {
                        // If all PM2.5 values are below 30, the first five items go to horizontalAirLiveData, the rest to verticalAirLiveData
                        _horizontalAirLiveData.value = take(MIN_HORIZONTAL_ITEMS)
                        _verticalAirLiveData.value = drop(MIN_HORIZONTAL_ITEMS)
                    } else {
                        // If there are items with PM2.5 value above 30, they are sent to verticalAirLiveData, the rest to horizontalAirLiveData
                        _horizontalAirLiveData.value = filter { it.pm25 <= 30 }
                        _verticalAirLiveData.value = filter { it.pm25 > 30 }
                    }
                }
            }
        }
    }

    companion object {
        // A constant that represents the minimum number of items we want to display in the horizontal recycler view
        private const val MIN_HORIZONTAL_ITEMS = 5
    }
}
