package tw.wesley.uiassignment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import tw.wesley.uiassignment.data.local.AirData
import tw.wesley.uiassignment.repo.AirDataRepository
import tw.wesley.uiassignment.viewmodel.AirQualityViewModel

@ExperimentalCoroutinesApi
class AirQualityViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private lateinit var viewModel: AirQualityViewModel
    private val airDataRepository = mockk<AirDataRepository>()
    private val airDataObserver: Observer<List<AirData>> = mockk(relaxed = true)

    private val horizontalAirDataObserver: Observer<List<AirData>> = mockk(relaxed = true)
    private val verticalAirDataObserver: Observer<List<AirData>> = mockk(relaxed = true)

    @Before
    fun setup() {
        coEvery { airDataRepository.getListAirDataFlow() } returns flowOf(
            listOf(
                AirData(
                    siteId = "DUMMY-siteId",
                    siteName = "DUMMY-siteName",
                    county = "DUMMY-county",
                    pm25 = 0,
                    status = "DUMMY-status",
                )
            )
        )
        coEvery { airDataRepository.fetchAndStoreAirQualityData() } just Runs
        viewModel = AirQualityViewModel(airDataRepository)
        viewModel.horizontalAirLiveData.observeForever(horizontalAirDataObserver)
        viewModel.verticalAirLiveData.observeForever(verticalAirDataObserver)
    }

    /**
     * Everytime we called viewModel.fetchAirData()
     * Make sure the horizontalAirLiveData and verticalAirLiveData are updated
     */
    @Test
    fun fetchAirData_ShouldUpdateLiveData() = runTest {
        viewModel.fetchAirData()

        verify(exactly = 1) { horizontalAirDataObserver.onChanged(any()) }
        verify(exactly = 1) { verticalAirDataObserver.onChanged(any()) }
    }
}
