package tw.wesley.uiassignment.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import tw.wesley.uiassignment.R
import tw.wesley.uiassignment.viewmodel.AirQualityViewModel

@AndroidEntryPoint // For HiltViewModel is used in our AirQualityViewModel
class MainActivity : AppCompatActivity() {

    // Use this delegate to auto create/destroy on correct lifecycle
    private val viewModel: AirQualityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launch {
            viewModel.fetchAirData()

        }
        lifecycleScope.launch {
            viewModel.horizontalAirDataFlow.collect { data ->
                Timber.d("collect/horizontal/${data.map { "${it.county}-${it.pm25}" }}")
            }
        }
        lifecycleScope.launch {
            viewModel.verticalAirDataFlow.collect { data ->
                Timber.d("collect/vertical/${data.map { "${it.county}-${it.pm25}" }}")
            }
        }


    }
}